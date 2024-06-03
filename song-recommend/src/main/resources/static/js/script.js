document.getElementById('recordbutton').addEventListener('click', async () => {
    const canvas = document.getElementById('canvas');
    const canvasCtx = canvas.getContext('2d');

    const audioCtx = new (window.AudioContext || window.webkitAudioContext)();
    const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
    const source = audioCtx.createMediaStreamSource(stream);

    const analyser = audioCtx.createAnalyser();
    analyser.fftSize = 256;
    const bufferLength = analyser.frequencyBinCount;
    const dataArray = new Uint8Array(bufferLength);

    source.connect(analyser);

    function frequencyToNote(frequency) {
        const A4 = 440;
        const A4_MIDI = 69;
        return Math.round(12 * Math.log2(frequency / A4) + A4_MIDI);
    }

    function getNoteName(midiNote) {
        const upNotes = ["A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"];
        return upNotes[midiNote % 12];
    }

    function draw() {
        requestAnimationFrame(draw);

        analyser.getByteFrequencyData(dataArray);

        canvasCtx.fillStyle = '#FFFFFF';
        canvasCtx.fillRect(0, 0, canvas.width, canvas.height);

        const barWidth = (canvas.width / bufferLength) * 5;
        const heightMultiplier = 2; // 증폭 계수
        let barHeight;
        let x = 0;

        // 글자 크기 조정
        canvasCtx.font = "16px Arial"; // 여기서 글자 크기를 조정합니다.

        for (let i = 0; i < bufferLength; i++) {
            barHeight = dataArray[i] * heightMultiplier;

            const frequency = i * (audioCtx.sampleRate / 2) / bufferLength;
            const midiNote = frequencyToNote(frequency);
            const noteName = getNoteName(midiNote);

            canvasCtx.fillStyle = '#00D344';
            canvasCtx.fillRect(x, canvas.height - barHeight / 2, barWidth, barHeight / 2);

            canvasCtx.fillStyle = '#000000';
            canvasCtx.fillText(noteName, x, canvas.height - 5); // 그래프 아래에 음표 고정 표시

            x += barWidth + 1;
        }
    }

    draw();
});
