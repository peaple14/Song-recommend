import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_sound/flutter_sound.dart';
import 'package:http/http.dart' as http;
import 'package:path_provider/path_provider.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Sound Example',
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  late FlutterSoundPlayer _player;
  late FlutterSoundRecorder _recorder;
  bool _isRecording = false;
  late String _recordedFilePath;

  @override
  void initState() {
    super.initState();
    _player = FlutterSoundPlayer();
    _recorder = FlutterSoundRecorder();
    _recorder.openAudioSession();
  }

  Future<void> _startRecording() async {
    try {
      String path = await _getAudioFilePath();
      await _recorder.startRecorder(
        toFile: path,
        codec: Codec.aacMP4,
      );
      setState(() {
        _isRecording = true;
        _recordedFilePath = path;
      });
    } catch (e) {
      print('Error starting recording: $e');
    }
  }

  Future<void> _stopRecording() async {
    try {
      await _recorder.stopRecorder();
      setState(() {
        _isRecording = false;
      });
    } catch (e) {
      print('Error stopping recording: $e');
    }
  }

  Future<void> _uploadAudioFile(String filePath) async {
    try {
      File file = File(filePath);
      var request = http.MultipartRequest('POST', Uri.parse('http://example.com/upload'));
      request.files.add(await http.MultipartFile.fromPath('audio', file.path));
      var response = await request.send();
      print('Server response: ${response.statusCode}');
    } catch (e) {
      print('Error uploading audio file: $e');
    }
  }

  Future<void> _sendToServer() async {
    if (_recordedFilePath != null) {
      await _uploadAudioFile(_recordedFilePath);
      // 삭제할 때 에러가 나면 무시하도록 try-catch
      try {
        await File(_recordedFilePath).delete();
      } catch (e) {
        print('Error deleting file: $e');
      }
    }
  }

  Future<String> _getAudioFilePath() async {
    Directory appDir = await getApplicationDocumentsDirectory();
    return '${appDir.path}/audio_recording.aac';
  }

  @override
  void dispose() {
    _recorder.closeAudioSession();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Flutter Sound Example'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            _isRecording
                ? ElevatedButton(
              onPressed: _stopRecording,
              child: Text('Stop Recording'),
            )
                : ElevatedButton(
              onPressed: _startRecording,
              child: Text('Start Recording'),
            ),
            SizedBox(height: 16),
            ElevatedButton(
              onPressed: _sendToServer,
              child: Text('Send to Server'),
            ),
          ],
        ),
      ),
    );
  }
}
