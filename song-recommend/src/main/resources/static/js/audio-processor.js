// Conversions to and from frequencies based on technique used at
// https://www.johndcook.com/music_hertz_bark.html

// Lookup arrays for note names.
// 음계이름 조회용
const keysSharp = ['C', 'C#', 'D', 'D#', 'E', 'F', 'F#', 'G', 'G#', 'A', 'A#', 'B'];
const keysFlat = ['C', 'Db', 'D', 'Eb', 'E', 'F', 'Gb', 'G', 'Ab', 'A', 'Bb', 'B'];


// Lookup table for steps, used to convert a key (e.g. `F#5`) to a frequency.
// 음계를 주파수로 변경하기전 조회용 const
const steps = {
C: 0,
   'C#': 1,
   Db: 1,
   D: 2,
   'D#': 3,
   Eb: 3,
   E: 4,
   F: 5,
   'F#': 6,
   Gb: 6,
   G: 7,
   'G#': 8,
   Ab: 8,
   A: 9,
   'A#': 10,
   Bb: 10,
   B: 11
};

//a4의 주파수
const a4 = 440;

/**
 * Options for parsing audio data.
 * @typedef {Object} AnalyseAudioDataOptions
 * @property {number} a4 Frequency of A4. Defaults to `440`.
 * @property {number} sampleRate Sample rate of the audio data.
 * @property {Float32Array} audioData The audio data to analyse.
 * @property {string} accidentals Whether to use sharps or flats. Defaults to `flats`.
 */

/**
 * Analyses audio data to extract pitch and other details. Returns null if audio
 * data could not be parsed.
 * @param {AnalyseAudioDataOptions} options Options for parsing.
 */
//오디오 데이터를 분석해 피치및 기타값 추출,안될시 null반환
function analyseAudioData (sampleRate, audioData, accidentals = 'sharps') {
  const frequency = YINDetector(audioData, sampleRate);
  if (frequency === null) {
    return null;
  }

  // Convert the frequency to a musical pitch.
  // 주파수 음계변환

  /* eslint-disable capitalized-comments */
  // c = a(2^-4.75)
  const c0 = a4 * Math.pow(2.0, -4.75);
  // h = round(12log2(f / c))
  const halfStepsBelowMiddleC = Math.round(12.0 * Math.log2(frequency / c0));
  // o = floor(h / 12)
  const octave = Math.floor(halfStepsBelowMiddleC / 12.0);
  const keys = accidentals === 'flats' ? keysFlat : keysSharp;
  const key = keys[Math.floor(halfStepsBelowMiddleC % 12)];

  // Obtain the correct frequency, in hertz, of the pitch the audio is at,
  // and then use that value determine how many cents the audio is off by.
  //오디오가 있는 음계의 주파수얻고,오디오가 얼마나 오프되었는지 확인.

  // z = fround(c * 2^((s + 12o) / 12))
  const correctHz = Math.fround(c0 * Math.pow(2.0, (steps[key] + (12 * octave)) / 12.0));
  // w = 1200log2(f / z)
  const centsOff = 1200 * Math.log2(frequency / correctHz);
  /* eslint-enable capitalized-comments */
  return {frequency, octave, key, correctHz, centsOff};
}

//음계와 옥타브에 대해 주파수 반환
function noteToFrequency(key, octave) {
  const c0 = a4 * Math.pow(2.0, -4.75);
  return Math.fround(c0 * Math.pow(2.0, (steps[key] + (12 * octave)) / 12.0));
}

//주어진 음계와 옥타브 연주
function playNote(key, octave) {
  let oscillator = audioContext.createOscillator();
  oscillator.type = "sine";
  oscillator.connect(audioContext.destination);
  oscillator.frequency.setValueAtTime(noteToFrequency(key, octave), audioContext.currentTime);
  oscillator.start();
  oscillator.stop(audioContext.currentTime + 2);
}

//play버튼이 있을때 음을 재생
let play = document.getElementById("play");
if (play != null) {
  play.onclick = function() {
    let key = noteToSing.innerHTML.substring(17, noteToSing.innerHTML.length - 2);
    let octave = noteToSing.innerHTML.charAt(noteToSing.innerHTML.length - 2);
    playNote(key, octave);
  }
}
