// 서버에서 기준 음표 가져오기
let base = sessionStorage.getItem('baseNote');
// 서버에서 기준 음표 가져오는 코드 추가

let text = document.createElement("p"); // p 요소 생성
let node = document.createTextNode("Your base note was " + base + "."); // 텍스트 노드 생성
text.append(node); // 텍스트 노드를 p 요소에 추가
body.insertBefore(text, body.childNodes[3]); // p 요소를 body 요소의 자식 노드 중 네 번째로 삽입

let upNotes = ["A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"]; // 상향 음표 배열
let downNotes = ["G#", "G", "F#", "F", "E", "D#", "D", "C#", "C", "B", "A#", "A"]; // 하향 음표 배열
let baseIndex; // 기준 음표 인덱스
if (window.location.pathname == "/second-step") {
  baseIndex = upNotes.findIndex(element => {
    return element === base.substring(0, base.length - 1);
  });
} else {
  baseIndex = downNotes.findIndex(element => {
    return element == base.substring(0, base.length - 1);
  });
}

let i = 1; // 반복 변수 초기화
let octaveNumber = base.charAt(base.length -1); // 기준 음표의 옥타브 번호 가져오기
let prevOctave = octaveNumber; // 이전 옥타브 번호 설정
let note = base.substring(0, base.length - 1); // 기준 음표의 음계 이름 가져오기
let prevNote = note; // 이전 음계 이름 설정

function nextNote() {
  prevNote = note; // 이전 음표 업데이트
  if (window.location.pathname == "/second-step") { // 두 번째 단계인 경우
    note = upNotes[(baseIndex + i) % 12]; // 상향 음표 배열에서 다음 음표 가져오기
    if (note === "C") {
      octaveNumber++; // 다음 옥타브로 이동
    } else if (note === "C#") {
      prevOctave = octaveNumber; // 이전 옥타브 업데이트
    }
  } else { // 두 번째 단계가 아닌 경우
    note = downNotes[(baseIndex + i) % 12]; // 하향 음표 배열에서 다음 음표 가져오기
    if (note === "B") {
      octaveNumber--; // 이전 옥타브로 이동
    } else if (note === "A#") {
      prevOctave = octaveNumber; // 이전 옥타브 업데이트
    }
  }
  let singText = document.createElement("p"); // p 요소 생성
  let sing = document.createTextNode("Now, try to sing " + note + octaveNumber + "."); // 텍스트 노드 생성
  singText.append(sing); // 텍스트 노드를 p 요소에 추가
  singText.setAttribute("id", "noteToSing"); // id 속성 설정
  body.insertBefore(singText, body.childNodes[4]); // p 요소를 body 요소의 자식 노드 중 다섯 번째로 삽입
  i++; // 반복 변수 증가
}
nextNote(); // 다음 음표 표시 함수 호출

function pass() {
  let noteToSing = document.getElementById("noteToSing"); // noteToSing 요소 가져오기
  console.log("noteToSing 요소 가져왔을경우");
  if (noteToSing != null) { // 요소가 존재하는 경우
    noteToSing.parentNode.removeChild(noteToSing); // noteToSing 요소 삭제
  } else{
    console.log("noteToSing 요소 없을 경우");
  }
  nextNote(); // 다음 음표 표시 함수 호출
}


function fail() {
  let next = document.createElement("BUTTON"); // 버튼 요소 생성
  next.innerHTML = "Next Step"; // 버튼 텍스트 설정

  let text = document.createElement("p"); // p 요소 생성
  let node;

  if (window.location.pathname == "/second-step") { // 두 번째 단계인 경우
    sessionStorage.setItem('highNote', prevNote + prevOctave); // 최고 음표를 세션 스토리지에 저장
    next.setAttribute("onclick", "location.href = 'third-step'"); // 다음 단계로 이동하는 onclick 이벤트 설정
    node = document.createTextNode("Your highest note is " + prevNote + prevOctave + "."); // 텍스트 노드 생성
  } else { // 두 번째 단계가 아닌 경우
    sessionStorage.setItem('lowNote', prevNote + prevOctave); // 최저 음표를 세션 스토리지에 저장
    next.setAttribute("onclick", "location.href = 'results'"); // 결과 페이지로 이동하는 onclick 이벤트 설정
    node = document.createTextNode("Your lowest note is " + prevNote + prevOctave + "."); // 텍스트 노드 생성
  }

  text.append(node); // 텍스트 노드를 p 요소에 추가

  let tryagain = document.createElement("BUTTON"); // 버튼 요소 생성
  tryagain.innerHTML = "Try Again"; // 버튼 텍스트 설정
  tryagain.onclick = function() { // 클릭 이벤트 핸들러 설정
    tryagain.parentNode.removeChild(tryagain); // tryagain 버튼 삭제
    if (next != null) { // next 버튼이 존재하는 경우
      next.parentNode.removeChild(next); // next 버튼 삭제
    }
    if (text != null) { // text 요소가 존재하는 경우
      text.parentNode.removeChild(text); // text 요소 삭제
    }
    recordbutton.disabled = false; // 녹음 버튼 활성화
  }

  let div = document.createElement("div"); // div 요소 생성
  div.setAttribute("class", "divider"); // 클래스 속성 설정

  body.append(text); // text 요소를 body 요소에 추가
  body.append(tryagain); // tryagain 버튼을 body 요소에 추가
  body.append(div); // div 요소를 body 요소에 추가
  body.append(next); // next 버튼을 body 요소에 추가

  recordbutton.disabled = true; // 녹음 버튼 비활성화
}
