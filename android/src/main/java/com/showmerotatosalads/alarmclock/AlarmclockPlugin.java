package com.showmerotatosalads.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.provider.AlarmClock;

import org.json.JSONArray;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** AlarmclockPlugin */
public class AlarmclockPlugin implements MethodCallHandler {
  private final Activity activity;

  private AlarmclockPlugin(Activity activity) {
    this.activity = activity;
  }

  /**
   * Plugin registration.
   */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "alarmclock");
    channel.setMethodCallHandler(new AlarmclockPlugin(registrar.activity()));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("setAlarm")) {
      boolean ok = startAlarmClockActivity(call);
      if (ok) {
        result.success(true);
      } else {
        result.success(false);
      }
    } else {
      result.notImplemented();
    }
  }

  public boolean startAlarmClockActivity(MethodCall call) {
    try {
      Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
      JSONArray args = (JSONArray) call.arguments;

      boolean skipui = args.getBoolean(0);
      String hour = args.getString(1);
      String minute = args.getString(2);
      String message = args.getString(3);

      i.putExtra(AlarmClock.EXTRA_SKIP_UI, skipui);
      i.putExtra(AlarmClock.EXTRA_HOUR, hour);
      i.putExtra(AlarmClock.EXTRA_MINUTES, minute);
      i.putExtra(AlarmClock.EXTRA_MESSAGE, message);

      activity.startActivity(i);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
