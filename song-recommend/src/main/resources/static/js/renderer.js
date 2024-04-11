const audioContext = new AudioContext(); // 오디오 컨텍스트 생성
let mediaRecorder; // 미디어 레코더 객체
let sourceStream; // 소스 스트림 객체
let refreshHandle; // 업데이트 핸들 객체
let noteArray = []; // 음표 배열

let body = document.querySelector("body"); // body 요소 선택용

let recordbutton = document.getElementById("recordbutton"); // 녹음 버튼 요소 선택

recordbutton.onclick = () => { // 녹음 버튼 클릭 event handler
  console.log("눌렀음")
  getMedia().then(() => {
    // 미디어 가져오기가 성공한 후에 실행할 코드를 작성합니다.
    console.log("getMedia 성공")
  }).catch(error => {
    // 미디어 가져오기가 실패한 경우에 대한 오류 처리를 수행합니다.
    console.log("getMedia 실패")
  });  recordbutton.innerHTML = "Recording"; // 녹음 중 텍스트 변경(녹음되고 있다고)
  recordbutton.disabled = true;  // 녹음 버튼 비활성화(녹음 중 안되게)
  if (window.location.pathname !== "/first-step") { // 현재 파일이 이게 아닐 경우(현재 주소가 아니게 될 때로 변경해야 함)
    console.log("재생버튼 비활성화 완료")
    play.disabled = true; // 재생 버튼 비활성화
  }
};

async function getMedia() { // 미디어 가져오기 함수
  try {
    sourceStream = await navigator.mediaDevices.getUserMedia({audio: true}); // 오디오 미디어 가져옴
    listen(); // 녹음 시작
    let complete = document.getElementById("complete"); // 완료 메세지 가져옴
    if (complete != null) { // 만약 완료 메세지 요소 있을 시
      complete.parentNode.removeChild(complete); // 완료 메세지 제거
    }
    let yousang = document.getElementById("yousang"); // yousang 메세지 요소 가져옴
    if (yousang != null) { // yousang 메세지 요소 있을 시
      yousang.parentNode.removeChild(yousang); // 메세지 제거
    }
  } catch(error) { // 오류용 catch
    console.log(error); // 콘솔에 오류 표시
    alert("Please enable your microphone."); // 마이크 활성화하라고 경고창
    recordbutton.innerHTML = "Record"; // 녹음 중 다시 Record로 변경
    recordbutton.disabled = false; // 녹음 버튼 활성화
    if (window.location.pathname !== "/first-step") { // 현재 주소가 "first-step.html"이 아닌 경우(주소로 변경 해야함)
      play.disabled = false; // 재생 버튼 활성화
    }
  };
}

/**
 * Starts listening for audio.
 */
function listen () { // 오디오 녹음용 함수
  mediaRecorder = new MediaRecorder(sourceStream); // 미디어 레코더 생성

  mediaRecorder.ondataavailable = update; // 레코딩 데이터가 가능할 시 update 함수 호출

  // Every 250ms, send whatever has been recorded to the audio processor.
  // This can't be done with `mediaRecorder.start(ms)` because the
  // `AudioContext` may fail to decode the audio data when sent in parts.
  refreshHandle = setInterval(() => { // 주기적으로 실행되는 업데이트 핸들러
    mediaRecorder.start(); // 레코딩 시작
    setTimeout(() => mediaRecorder.stop(), 250); // 250ms 후에 레코딩 중지
  }, 500); // 0.5초 간격으로 실행
}

/**
 * Stops listening for audio.
 */
function stop () { // 오디오 녹음 중지 함수
  clearInterval(refreshHandle); // 업데이트 핸들러 종료
  let text = document.createElement("p"); // 새 p 요소 생성
  let node = document.createTextNode("Recording Complete."); // 텍스트 노드 생성
  console.log("완료노드 되도록.")
  text.setAttribute("id", "complete"); // id 속성 설정
  text.append(node); // 텍스트 노드 추가
  body.append(text); // body에 요소 추가
  recordbutton.innerHTML = "Record"; // 녹음 버튼 텍스트 변경
  recordbutton.disabled = false; // 녹음 버튼 활성화
  if (window.location.pathname !== "/first-step") { // 현재 파일이 "first-step.html"이 아닌 경우(주소로 변경 해야함)
    play.disabled = false; // 재생 버튼 활성화
  }
}

/**
 * Handles data received from a `MediaRecorder`.
 * @param {BlobEvent} e Blob event from the `MediaRecorder`.
 */
async function update (e) { // 업데이트 함수
  if (e.data.size !== 0) { // 레코딩 데이터가 있는 경우
    await process(e.data); // 데이터 처리 함수 호출
  }
}

/**
 * Sends audio data to the audio processing worker.
 * @param {Blob} data The blob containing the recorded audio data.
 */
async function process(data) { // 데이터 처리 함수
                               // Load the blob.
  const response = await fetch(URL.createObjectURL(data)); // Blob 데이터 로드
  const arrayBuffer = await response.arrayBuffer(); // ArrayBuffer로 변환
  // Decode the audio.
  const audioBuffer = await audioContext.decodeAudioData(arrayBuffer); // 오디오 디코딩
  const audioData = audioBuffer.getChannelData(0); // 오디오 데이터 추출
  const sampleRate = audioBuffer.sampleRate; // 샘플링 레이트 추출
  const pitch = analyseAudioData(sampleRate, audioData, accidentals = 'sharps'); // 오디오 분석

  updateNoteArray(pitch); // 음표 배열 업데이트
  if (noteArray.length == 2) { // 음표 배열의 길이가 2인 경우
    stop(); // 녹음 중지
    let note = noteArray[0]; // 첫 번째 음표 추출
    if (window.location.pathname == "/first-step") { // 현재 파일이 "first-step.html"인 경우(주소로 변경)
      console.log("여기까진됨.")
      sessionStorage.setItem("baseNote", note); // 세션 스토리지에 기준 음표 저장(됨)
      let text = document.createElement("p"); // 새 p 요소 생성
      let node = document.createTextNode("Your base note is " + note + "."); // 텍스트 노드 생성
      text.append(node); // 텍스트 노드 추가
      body.append(text); // body에 요소 추가

      let button = document.createElement("BUTTON"); // 새 버튼 요소 생성
      button.innerHTML = "Next Step"; // 버튼 텍스트 설정
      button.setAttribute("onclick", "location.href = 'second-step'"); // 버튼 클릭 이벤트 설정
      body.append(button); // body에 버튼 추가
    } else { // 현재 파일 이름이 "first-step.html"이 아닌 경우 -> 또 주소로 변경 해야함
      let text = document.createElement("p"); // 새 p 요소 생성

      let noteToSing = document.getElementById("noteToSing"); // 노래할 음표 요소 가져오기
      let goal = noteToSing.innerHTML.substring(17, noteToSing.innerHTML.length - 1); // 목표 음표 추출
      if (note === goal) { // 노래한 음표가 목표 음표와 같은 경우
        let node = document.createTextNode("Nice! You correctly sang " + note + "."); // 텍스트 노드 생성
        text.setAttribute("id", "yousang"); // id 속성 설정
        text.append(node); // 텍스트 노드 추가
        body.append(text); // body에 요소 추가
        pass(); // 성공 처리 함수 호출
      } else { // 노래한 음표가 목표 음표와 다른 경우
        let node = document.createTextNode("Oops! You sang " + note + " instead of " + goal + ".");  // 텍스트 노드 생성
        text.setAttribute("id", "yousang"); // id 속성 설정
        text.append(node); // 텍스트 노드 추가
        body.append(text); // body에 요소 추가
        fail(); // 실패 처리 함수 호출
      }
    }
  }
}

function updateNoteArray(pitch) { // 음표 배열 업데이트 함수
  let previousNote; // 이전 음표
  if (noteArray.length > 0) { // 음표 배열이 비어 있지 않은 경우
    previousNote = noteArray[noteArray.length - 1]; // 이전 음표 설정
  }
  // pitch가 null이거나 옥타브가 9를 초과하거나, 이전 음표와 다른 경우
  if (pitch == null || pitch.octave > 9 || (pitch.key + pitch.octave != previousNote && previousNote != null)) {
    noteArray.length = 0; // 음표 배열 초기화
  } else { // 위의 조건에 해당하지 않는 경우
    noteArray.push(pitch.key + pitch.octave) // 음표 배열에 음표 추가
  }
}
