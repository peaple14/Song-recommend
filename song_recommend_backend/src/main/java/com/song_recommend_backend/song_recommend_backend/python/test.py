from pydub import AudioSegment
import numpy as np
import json
import sys

def find_pitch_range(audio_file_path):
    # 오디오 파일 로드
    audio = AudioSegment.from_file(audio_file_path)

    # NumPy 배열로 변환
    samples = np.array(audio.get_array_of_samples())

    # 피치 추출
    pitch = np.fft.fft(samples)
    pitch = np.abs(pitch[:len(pitch)//2])

    # 피치의 주파수 대역 추출
    freqs = np.fft.fftfreq(len(pitch), 1/audio.frame_rate)
    freqs = freqs[:len(freqs)//2]

    # 주파수에서 가장 큰 값과 가장 작은 값의 인덱스 찾기
    max_freq_idx = np.argmax(pitch)
    min_freq_idx = np.argmin(pitch)

    # 주파수 배열의 크기 확인 및 수정
    if max_freq_idx >= len(freqs):
        max_freq_idx = len(freqs) - 1
    if min_freq_idx >= len(freqs):
        min_freq_idx = len(freqs) - 1

    # 인덱스를 주파수로 변환하여 MIDI 노트로 변환
    max_freq = freqs[max_freq_idx]
    min_freq = freqs[min_freq_idx]

    # 결과를 JSON 형식으로 출력
    result = {
        "max_midi_note": 69 + 12 * np.log2(max_freq / 440),
        "min_midi_note": 69 + 12 * np.log2(min_freq / 440),
        "max_freq": max_freq,
        "min_freq": min_freq
    }

    print(json.dumps(result))

#윗부분은 알고리즘 새로 짜서 가져오기


# 자바에서 가져온 경로
if __name__ == "__main__":
    audio_path = sys.argv[1]
    find_pitch_range(audio_path)