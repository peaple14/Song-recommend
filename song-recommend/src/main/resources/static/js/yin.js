//여기가 알고리즘의 핵심일것 같음.

/*
   Copyright (C) 2003-2009 Paul Brossier <piem@aubio.org>
   This file is part of aubio.
   aubio is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   aubio is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   You should have received a copy of the GNU General Public License
   along with aubio.  If not, see <http://www.gnu.org/licenses/>.
 */

/* This algorithm was developed by A. de Cheveigné and H. Kawahara and
 * published in:
 * 
 * de Cheveigné, A., Kawahara, H. (2002) "YIN, a fundamental frequency
 * estimator for speech and music", J. Acoust. Soc. Am. 111, 1917-1930.  
 *
 * see http://recherche.ircam.fr/equipes/pcm/pub/people/cheveign.html
 */

var threshold = 0.10; // 임계값
var probabilityThreshold = 0.1; // 확률 임계값

function YINDetector(float32AudioBuffer, sampleRate) {
  // 제공된 버퍼의 길이보다 작은 최대 2의 거듭제곱으로 버퍼 크기 설정

  var bufferSize = void 0;
  for (bufferSize = 1; bufferSize < float32AudioBuffer.length; bufferSize *= 2) {}
  bufferSize /= 2;

  // YIN 논문의 단계 1에 설명된대로 yinBuffer 설정
  var yinBufferLength = bufferSize / 2;
  var yinBuffer = new Float32Array(yinBufferLength);

  var probability = void 0,
      tau = void 0;

  // YIN 논문의 단계 2에 설명된대로 차이 함수 계산
  for (var t = 0; t < yinBufferLength; t++) {
    yinBuffer[t] = 0;
  }
  for (var _t = 1; _t < yinBufferLength; _t++) {
    for (var i = 0; i < yinBufferLength; i++) {
      var delta = float32AudioBuffer[i] - float32AudioBuffer[i + _t];
      yinBuffer[_t] += delta * delta;
    }
  }

  // YIN 논문의 단계 3에 설명된대로 누적 평균 정규화 차이 계산
  yinBuffer[0] = 1;
  yinBuffer[1] = 1;
  var runningSum = 0;
  for (var _t2 = 1; _t2 < yinBufferLength; _t2++) {
    runningSum += yinBuffer[_t2];
    yinBuffer[_t2] *= _t2 / runningSum;
  }

  // YIN 논문의 단계 4에 설명된대로 절대 임계값 계산
  // 배열의 처음 두 위치가 1이므로
  // 세 번째 위치부터 시작할 수 있습니다.
  for (tau = 2; tau < yinBufferLength; tau++) {
    if (yinBuffer[tau] < threshold) {
      while (tau + 1 < yinBufferLength && yinBuffer[tau + 1] < yinBuffer[tau]) {
        tau++;
      }
        // tau를 찾았습니다. 루프를 종료하고 반환합니다.
        // 확률 저장
        // YIN 논문에서는 임계값이 주어지며,
        // 이는 허용된 후보 목록을 결정하고
        // 주기적인 신호 내의 비주기성 파워의 비율로 해석됩니다.
        // 따라서 주기성은 비주기성의 보완이므로:
        // 주기성 = 1 - 비주기성

      probability = 1 - yinBuffer[tau];
      break;
    }
  }

  // 피치를 찾지 못한 경우 null 반환
  if (tau == yinBufferLength || yinBuffer[tau] >= threshold) {
    return null;
  }

  // 확률이 너무 낮은 경우 -1 반환
  if (probability < probabilityThreshold) {
    return null;
  }

  /**
   * AUBIO_YIN 논문의 단계 5를 구현합니다. 적합한 tau 값을 사용하여 추정값을 더 정밀하게 조정합니다.
   * 이것은 더 높은 주파수를 더 정확하게 감지하기 위해 필요합니다.
   * http://fizyka.umk.pl/nrbook/c10-2.pdf 및
   * http://fedc.wiwi.hu-berlin.de/xplore/tutorials/xegbohtmlnode62.html을 참조하십시오.
   */
  var betterTau = void 0,
      x0 = void 0,
      x2 = void 0;
  if (tau < 1) {
    x0 = tau;
  } else {
    x0 = tau - 1;
  }
  if (tau + 1 < yinBufferLength) {
    x2 = tau + 1;
  } else {
    x2 = tau;
  }
  if (x0 === tau) {
    if (yinBuffer[tau] <= yinBuffer[x2]) {
      betterTau = tau;
    } else {
      betterTau = x2;
    }
  } else if (x2 === tau) {
    if (yinBuffer[tau] <= yinBuffer[x0]) {
      betterTau = tau;
    } else {
      betterTau = x0;
    }
  } else {
    var s0 = yinBuffer[x0];
    var s1 = yinBuffer[tau];
    var s2 = yinBuffer[x2];
// fixed AUBIO implementation, thanks to Karl Helgason:
    // (2.0f * s1 - s2 - s0)가 잘못된 부분이었습니다.
    betterTau = tau + (s2 - s0) / (2 * (2 * s1 - s2 - s0));
  }

  return sampleRate / betterTau;
}
