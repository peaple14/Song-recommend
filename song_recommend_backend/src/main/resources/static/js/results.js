let lowNote = localStorage.getItem('lowNote'); // 로컬 스토리지에서 최저 음표 가져오기
let highNote = localStorage.getItem('highNote'); // 로컬 스토리지에서 최고 음표 가져오기
let body = document.querySelector("body"); // body 요소 선택
let text = document.createElement("p"); // p 요소 생성
let node = document.createTextNode(lowNote + " to " + highNote); // 텍스트 노드 생성
text.append(node); // 텍스트 노드를 p 요소에 추가
body.insertBefore(text, body.childNodes[1]); // p 요소를 body 요소의 자식 노드 중 두 번째로 삽입


//서버를 넘길때마다 로컬 스토리지에 담겨있는것들은 같이 넘어가지 않으니, 서버에 저장해야함.