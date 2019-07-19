package com.greetsweet.plant.greetapp.EditorFrags;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.greetsweet.plant.ColorPickerAdapter;
import com.greetsweet.plant.FontWork.FontModelClass;
import com.greetsweet.plant.FontWork.FontsAdapterClass;
import com.greetsweet.plant.GridSpacing.GridSpacingItemDecoration;
import com.greetsweet.plant.Handler.ServiceHandler;
import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.RecyclerListner.RecyclerItemClickListener;
import com.greetsweet.plant.greetapp.AdapterClasses.FramesAdapterClass;
import com.greetsweet.plant.greetapp.AdapterClasses.NewAdapterClass;
import com.greetsweet.plant.greetapp.AdapterClasses.UserGreetingAdapterClass;
import com.greetsweet.plant.greetapp.ModalClasses.FrameModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.Frame_list;
import com.greetsweet.plant.greetapp.ModalClasses.Logos;
import com.greetsweet.plant.greetapp.ModalClasses.UserGreetingModalClass;
import com.greetsweet.plant.greetapp.ModalClasses.UserGreetings;
import com.greetsweet.plant.greetapp.UserGreets.UserGreetingsActivity;
import com.greetsweet.plant.mylibs.utility.Cons;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class FrameEditorDialogFragment extends DialogFragment {

    public static final String TAG = FrameEditorDialogFragment.class.getSimpleName();
    public static final String EXTRA_INPUT_TEXT = "extra_input_text";
    public static final String EXTRA_COLOR_CODE = "extra_color_code";
    private EditText mAddTextEditText;
    private TextView mAddTextDoneTextView;
    private InputMethodManager mInputMethodManager;
    private int mColorCode;
    private TextEditor mTextEditor;
    TextView robotobtn,lucidabtn,adlerybtn, togbtn, blanchbtn;

    TextView abhibtn, afterbtn, amongbtn, anittobtn, aussbtn,backbtn,bessbtn,blaclbutterbtn,bluebtn;

    ///morefonts

    private String abhinaya ="Abhinaya.ttf";
    private String aldery ="Adlery-Swash-trial.ttf";
    private String afterkilly ="AfterkillyDemo.ttf";
    private String among ="Among.ttf";
    private String anitto ="Anitto.ttf";
    private String aussiente ="Aussiente.ttf";
    private String backhill ="Backhill.ttf";
    private String bessita ="Bessita.ttf";
    private String blackbutter ="Blackbutter.ttf";
    private String blanch ="beyond_wonderland.ttf";
    private String blue ="Bluebell.ttf";
    private String bombinate ="Bombinate.ttf";
    private String cendolia ="Cendolita.ttf";
    private String chapline ="Chapline.ttf";
    private String debtos ="Debtos.ttf";
    private String dettallia ="Dettallia.ttf";
    private String dingbod ="Dingbod.ttf";
    private String endolitta ="Endolitta.ttf";
    private String fondy ="FondyScript.ttf";
    private String greatvibes ="GreatVibes.ttf";
    private String herey ="Herey.ttf";
    private String Luci ="Lucida Handwriting Italic.ttf";
    private String mont ="Montserrat.ttf";
    private String motion ="MotionPicture.ttf";
    private String playfair ="Playfair.ttf";
    private String pure ="Pure.ttf";
    private String roboto ="Roboto-Black.ttf";
    private String together ="knownbetter.ttf";
    private String tuesday ="TuesdayVibes.ttf";
    private String vintage ="Vintages.ttf";


    private FontsAdapterClass adapterClass;
    private ArrayList<FontModelClass> data;
    private RecyclerView fontrecycler;

    Typeface facetype;
    String writingtype;
    AlertDialog dialog;
    ImageView morefont;

    FrameModalClass val;
    String frameurl ="http://13.232.178.62/api/greeting/frames/";
    private FramesAdapterClass adapter;
    RecyclerView framerecycler;

    String imageframeget;
    Bitmap bitmapimage;
    public interface TextEditor {
        void onDone();

    }


    //Show dialog with provide text and text color
    public static FrameEditorDialogFragment show(@NonNull AppCompatActivity appCompatActivity,
                                                 @NonNull String inputText,
                                                 @ColorInt int colorCode) {
        Bundle args = new Bundle();
        args.putString(EXTRA_INPUT_TEXT, inputText);
        args.putInt(EXTRA_COLOR_CODE, colorCode);
        FrameEditorDialogFragment fragment = new FrameEditorDialogFragment();
        fragment.setArguments(args);
        fragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return fragment;
    }

    //Show dialog with default text input as empty and text color white
    public static FrameEditorDialogFragment show(@NonNull AppCompatActivity appCompatActivity) {
        return show(appCompatActivity,
                "", ContextCompat.getColor(appCompatActivity, R.color.white));
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        //Make dialog full screen with transparent background
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frame_editor, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        imageframeget=PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.frameimage);

        try {


        } catch (Exception e) {
            e.getMessage();

        }

        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mAddTextDoneTextView = view.findViewById(R.id.add_text_done_tv);

        //Setup the color picker for text color
         framerecycler = view.findViewById(R.id.add_text_color_picker_recycler_view);
        framerecycler.setHasFixedSize(true);

         new Task_Frame().execute();
 //       mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        //Make a callback on activity when user is done with text editing
        mAddTextDoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                dismiss();


                mTextEditor.onDone();

              /*  String inputText =mAddTextEditText.getText().toString();

                PhotoApp.getInstance().createLog("PRINT NAME"+ inputText);
                if (!TextUtils.isEmpty(inputText) && mTextEditor != null) {

                    mTextEditor.onDone(inputText, mColorCode,facetype);

                }*/
            }
        });

    }
    public class Task_Frame extends AsyncTask<Void, Void, Void> {

        String statusCode;
        // private TransparentProgressDialog dialog;
        String responsemsg = "";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new TransparentProgressDialog(_mContext, R.drawable.loader);
            dialog.show();*/

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)

        {

            try {
                PhotoApp.getInstance().createLog("API HITT");

                JSONArray jobj = new JSONArray();

                ServiceHandler sh = new ServiceHandler();

                PhotoApp.getInstance().createLog("=====jobj+url======"+jobj+"========"+frameurl);

                String jsonStr = sh.postDataArrayGet(frameurl, jobj);



                if (jsonStr != null) {
                    //{"details":{"status":"post can not be deleted."},"statusCode":"200"}
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    PhotoApp.getInstance().createLog("Frame jsonObj RESPONSE "+jsonObj);

                    statusCode = jsonObj.getString("status");

                    PhotoApp.getInstance().createLog("Frame STATUS CODE  "+statusCode);
                    if (statusCode.equalsIgnoreCase("200")) {

                        responsemsg = "200";

                        JSONArray type_j = jsonObj.getJSONArray("frame_list");
                        val = new FrameModalClass();
                        for(int i=0;i<type_j.length();i++)
                        {


                            JSONObject j_homepage = type_j.getJSONObject(i);
                            Frame_list val_frame = new Frame_list();
                            val_frame.setImage(j_homepage.getString("image"));
                            val_frame.setId(j_homepage.getString("id"));


                            val.framelist.add(val_frame);




                        }

                        PhotoApp.getInstance().createLog("=======getLogoModalClasses========="+getLogoModalClasses.size());



                    } else {

                        PhotoApp.getInstance().createLog("===============no response =========");
                        responsemsg = "No response";
                    }


                }


            } catch (Exception e) {
                PhotoApp.getInstance().createLog("===============Exception e ========="+e.getMessage());
                e.printStackTrace();
            }

            return null;
        }




        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            try {
                progressDialog.dismiss();


                adapter = new FramesAdapterClass(val.framelist, getActivity());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                framerecycler.setLayoutManager(linearLayoutManager);
                framerecycler.setAdapter(adapter);


            } catch (Exception e) {
            }
            finally {
                try {
                    if (progressDialog != null) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }catch (NullPointerException e)
                {

                }
            }

        }


    }

    ArrayList<FrameModalClass> getLogoModalClasses = new ArrayList<FrameModalClass>();


    //Callback to listener if user is done with text editing
    public void setOnTextEditorListener(TextEditor textEditor) {
        mTextEditor = textEditor;
    }


}
