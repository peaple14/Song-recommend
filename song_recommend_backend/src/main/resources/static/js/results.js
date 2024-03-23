// let lowNote = localStorage.getItem('lowNote'); // 로컬 스토리지에서 최저 음표 가져오기
// let highNote = localStorage.getItem('highNote'); // 로컬 스토리지에서 최고 음표 가져오기

// 서버에서 최저 음표 가져오기
fetch('/get-low-note')
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        let lowNote = data.lowNote; // 서버에서 받은 최저 음표 데이터
        // 여기에 최저 음표를 처리하는 코드를 작성합니다.
    })
    .catch(error => {
        console.error('There was an error with the fetch operation:', error);
    });

// 서버에서 최고 음표 가져오기
fetch('/get-high-note')
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        let highNote = data.highNote; // 서버에서 받은 최고 음표 데이터
        // 여기에 최고 음표를 처리하는 코드를 작성합니다.
    })
    .catch(error => {
        console.error('There was an error with the fetch operation:', error);
    });





let body = document.querySelector("body"); // body 요소 선택
let text = document.createElement("p"); // p 요소 생성
// let node = document.createTextNode(lowNote + " to " + highNote); // 텍스트 노드 생성
// text.append(node); // 텍스트 노드를 p 요소에 추가
body.insertBefore(text, body.childNodes[1]); // p 요소를 body 요소의 자식 노드 중 두 번째로 삽입


