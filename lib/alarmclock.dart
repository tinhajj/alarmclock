import 'dart:async';

import 'package:flutter/services.dart';

class Alarmclock {
  static const MethodChannel _channel =
      const MethodChannel('alarmclock', JSONMethodCodec());

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<bool> setAlarm(
      {bool skipui = false,
      int hour = 12,
      int minute = 0,
      String message = "Flutter"}) async {
    bool ok = await _channel
        .invokeMethod('setAlarm', <dynamic>[skipui, hour, minute, message]);
    return ok;
  }
}
