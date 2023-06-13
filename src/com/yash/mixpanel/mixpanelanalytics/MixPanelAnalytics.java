package com.yash.mixpanel.mixpanelanalytics;

import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.OnDestroyListener;
import com.google.appinventor.components.runtime.util.YailDictionary;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MixPanelAnalytics extends AndroidNonvisibleComponent implements OnDestroyListener {
  public MixpanelAPI mixpanel;

  public MixPanelAnalytics(ComponentContainer container) {
    super(container.$form());
  }

  @SimpleFunction(description = "")
  public void Initialize(String token) {
    mixpanel = MixpanelAPI.getInstance(form.$context(), token);
    final String trackingDistinctId = mixpanel.getDistinctId();
    mixpanel.identify(trackingDistinctId);
    mixpanel.getPeople().identify(trackingDistinctId);
  }


  @SimpleFunction(description = "")
  public void NewEvent(String eventname, YailDictionary events){
    JSONObject jsonObject = null;
    try {
      jsonObject = new JSONObject(events.toString());
      JSONArray names = jsonObject.names();
      JSONObject props = new JSONObject();
      for (int i=0; i<jsonObject.length(); i++){
        String string = names.getString(i);
        Object object = jsonObject.get(string);
        props.put(string,object.toString());
        mixpanel.track(eventname, props);
      }
    } catch (JSONException ex) {
      ex.printStackTrace();
    }
  }

  @SimpleFunction(description = "")
  public void SimpleEvent(String eventName) {
    mixpanel.track(eventName);
    mixpanel.eventElapsedTime(eventName);
  }

  @SimpleFunction(description = "")
  public void AddUser(String name,String email) {
    mixpanel.getPeople().set("$name",name.toString());
    mixpanel.getPeople().set("$email",email.toString());
  }

  @SimpleFunction(description = "")
  public void ForceSend() {
    mixpanel.flush();
  }

  @SimpleFunction(description = "")
  public void UpdateUserDetails(YailDictionary details) {
    JSONObject jsonObject = null;
    try {
      jsonObject = new JSONObject(details.toString());
      JSONArray names = jsonObject.names();
      JSONObject userdetails = new JSONObject();

      for (int i=0; i<jsonObject.length(); i++){
        String string = names.getString(i);
        Object object = jsonObject.get(string);
        userdetails.put(string,object.toString());
        mixpanel.getPeople().set(userdetails);
      }
    } catch (JSONException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void onDestroy() {
    ForceSend();
  }
}
