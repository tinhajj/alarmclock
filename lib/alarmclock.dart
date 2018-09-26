import 'dart:async';

import 'package:flutter/services.dart';

class Alarmclock {
  static const MethodChannel _channel =
      const MethodChannel('alarmclock');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
