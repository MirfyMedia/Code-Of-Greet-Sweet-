package com.greetsweet.plant;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.greetsweet.plant.FontWork.FontModelClass;
import com.greetsweet.plant.FontWork.FontsAdapterClass;
import com.greetsweet.plant.RecyclerListner.RecyclerItemClickListener;

import java.util.ArrayList;


public class TextEditorDialogFragment extends DialogFragment {

    public static final String TAG = TextEditorDialogFragment.class.getSimpleName();
    public static final String EXTRA_INPUT_TEXT = "extra_input_text";
    public static final String EXTRA_COLOR_CODE = "extra_color_code";
    private EditText mAddTextEditText;
    private TextView mAddTextDoneTextView;
    private InputMethodManager mInputMethodManager;
    private int mColorCode;
    private TextEditor mTextEditor;


    TextView robotobtn,lucidabtn,adlerybtn, togbtn, blanchbtn;

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

    public interface TextEditor {
        void onDone(String inputText, int colorCode,Typeface typeface);
    }


    //Show dialog with provide text and text color
    public static TextEditorDialogFragment show(@NonNull AppCompatActivity appCompatActivity,
                                                @NonNull String inputText,
                                                @ColorInt int colorCode) {
        Bundle args = new Bundle();
        args.putString(EXTRA_INPUT_TEXT, inputText);
        args.putInt(EXTRA_COLOR_CODE, colorCode);
        TextEditorDialogFragment fragment = new TextEditorDialogFragment();
        fragment.setArguments(args);
        fragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return fragment;
    }

    //Show dialog with default text input as empty and text color white
    public static TextEditorDialogFragment show(@NonNull AppCompatActivity appCompatActivity) {
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
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_text_editor, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        fontrecycler=(RecyclerView)view.findViewById(R.id.fontrecycler);
        setData();
        recyclerlistnerfn();


        mAddTextEditText = view.findViewById(R.id.add_text_edit_text);


        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mAddTextDoneTextView = view.findViewById(R.id.add_text_done_tv);


       /* robotobtn =(TextView)view.findViewById(R.id.textView39);
        lucidabtn =(TextView)view.findViewById(R.id.textView40);
        adlerybtn =(TextView)view.findViewById(R.id.textView41);
        togbtn =(TextView)view.findViewById(R.id.textView42);
        blanchbtn =(TextView)view.findViewById(R.id.textView43);
        facetype = null;
        robotobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), roboto));
                facetype=Typeface.createFromAsset(getActivity().getAssets(),roboto);

            }
        });
        lucidabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Luci));
                facetype=Typeface.createFromAsset(getActivity().getAssets(),Luci);


            }
        });
        adlerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), aldery));
                facetype=Typeface.createFromAsset(getActivity().getAssets(),aldery);


            }
        });
        togbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), together));
                facetype=Typeface.createFromAsset(getActivity().getAssets(),together);


            }
        });
        blanchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), blanch));
                facetype=Typeface.createFromAsset(getActivity().getAssets(),blanch);


            }
        });
*/


        //Setup the color picker for text color
        RecyclerView addTextColorPickerRecyclerView = view.findViewById(R.id.add_text_color_picker_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        addTextColorPickerRecyclerView.setLayoutManager(layoutManager);
        addTextColorPickerRecyclerView.setHasFixedSize(true);
        ColorPickerAdapter colorPickerAdapter = new ColorPickerAdapter(getActivity());
        //This listener will change the text color when clicked on any color from picker
        colorPickerAdapter.setOnColorPickerClickListener(new ColorPickerAdapter.OnColorPickerClickListener() {
            @Override
            public void onColorPickerClickListener(int colorCode) {
                mColorCode = colorCode;
                mAddTextEditText.setTextColor(colorCode);
            }
        });
        addTextColorPickerRecyclerView.setAdapter(colorPickerAdapter);
        mAddTextEditText.setText(getArguments().getString(EXTRA_INPUT_TEXT));
        mAddTextEditText.setCursorVisible(true);
        mAddTextEditText.setEnabled(true);
        mAddTextEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        mAddTextEditText.setFocusable(true);

        mColorCode = getArguments().getInt(EXTRA_COLOR_CODE);
        mAddTextEditText.setTextColor(mColorCode);
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        //Make a callback on activity when user is done with text editing
        mAddTextDoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                dismiss();
                String inputText = mAddTextEditText.getText().toString();
                if (!TextUtils.isEmpty(inputText) && mTextEditor != null) {
                    mTextEditor.onDone(inputText, mColorCode,facetype);
                }
            }
        });
        mAddTextEditText.setCursorVisible(true);
        mAddTextEditText.requestFocus();

    }

    private void setData() {

        data=new ArrayList<>();
        data.add(new FontModelClass("Abhinaya"));
        data.add(new FontModelClass("Adlery"));
        data.add(new FontModelClass("Afterkilly"));
        data.add(new FontModelClass("Among"));
        data.add(new FontModelClass("Anitto"));
        data.add(new FontModelClass("Aussiente"));
        data.add(new FontModelClass("Backhill"));
        data.add(new FontModelClass("Bessita"));
        data.add(new FontModelClass("Blackbutter"));
        data.add(new FontModelClass("Blanch"));
        data.add(new FontModelClass("Bluebell"));
        data.add(new FontModelClass("Bombinate"));
        data.add(new FontModelClass("Cendolita"));
        data.add(new FontModelClass("Chapline"));
        data.add(new FontModelClass("Debtos"));
        data.add(new FontModelClass("Dettallia"));
        data.add(new FontModelClass("Dingbod"));
        data.add(new FontModelClass("Endolitta"));
        data.add(new FontModelClass("FonyScript"));
        data.add(new FontModelClass("GreatVibes"));
        data.add(new FontModelClass("Herey"));
        data.add(new FontModelClass("Lucida"));
        data.add(new FontModelClass("Montserrat"));
        data.add(new FontModelClass("Motion Picture"));
        data.add(new FontModelClass("Playfair"));
        data.add(new FontModelClass("Pure"));
        data.add(new FontModelClass("Roboto"));
        data.add(new FontModelClass("Together"));
        data.add(new FontModelClass("Tuesday Vibes"));
        data.add(new FontModelClass("Vintage"));



        adapterClass=new FontsAdapterClass(data,getActivity());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        fontrecycler.setLayoutManager(linearLayoutManager);
        fontrecycler.setAdapter(adapterClass);
    }

    private void recyclerlistnerfn() {
        fontrecycler.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), fontrecycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {



                        if (position == 0) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), abhinaya));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),abhinaya);

                        }
                        if (position == 1) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), aldery));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),aldery);

                        }
                        if (position == 2) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), afterkilly));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),afterkilly);

                        }
                        if (position == 3) {

                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), among));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),among);


                        }  if (position == 4) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), anitto));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),anitto);


                        }  if (position == 5) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), aussiente));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),aussiente);

                        }
                        if (position == 6) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), backhill));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),backhill);

                        }
                        if (position == 7) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), bessita));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),bessita);

                        }

                        if (position == 8) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), blackbutter));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),blackbutter);

                        }
                        if (position == 9) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), blanch));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),blanch);

                        }
                        if (position == 10) {

                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), blue));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),blue);


                        }  if (position == 11) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), bombinate));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),bombinate);


                        }  if (position == 12) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), cendolia));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),cendolia);

                        }
                        if (position == 13) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), chapline));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),chapline);

                        }
                        if (position == 14) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), debtos));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),debtos);

                        }
                        if (position == 15) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), dettallia));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),dettallia);

                        }
                        if (position == 16) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), dingbod));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),dingbod);

                        }
                        if (position == 17) {

                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), endolitta));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),endolitta);


                        }  if (position == 18) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), fondy));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),fondy);


                        }  if (position == 19) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), greatvibes));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),greatvibes);

                        }
                        if (position == 20) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), herey));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),herey);

                        }
                        if (position == 21) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Luci));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),Luci);

                        }
                        if (position == 22) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), mont));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),mont);

                        }
                        if (position == 23) {

                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), motion));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),motion);


                        }  if (position == 24) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), playfair));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),playfair);


                        }  if (position == 25) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), pure));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),pure);

                        }

                        if (position == 26) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),roboto));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),roboto);

                        }
                        if (position == 27) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), together));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),together);

                        }
                        if (position == 28) {
                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), tuesday));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),tuesday);

                        }
                        if (position == 29) {

                            mAddTextEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), vintage));
                            facetype=Typeface.createFromAsset(getActivity().getAssets(),vintage);


                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    //Callback to listener if user is done with text editing
    public void setOnTextEditorListener(TextEditor textEditor) {
        mTextEditor = textEditor;
    }
}
