package com.myproject.novel.ui.user.updateInfo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.GlideApp;
import com.myproject.novel.local.util.SharedPreferencesUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.local.util.customfonts.EditText_Poppins_Regular;
import com.myproject.novel.local.util.customfonts.MyTextView_Poppins_Medium;
import com.myproject.novel.model.ProfileModel;
import com.myproject.novel.ui.user.UserFragment;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;


public class UpdateInfoBottomSheetDialog extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener {

    private final UserFragment userFragment;
    private View rootView;
    private EditText_Poppins_Regular genderInput, nicknameInput, birthDayInput;
    private String mBirthDayTxt;
    private CircleImageView avatar;
    private Uri imageChoice;
    private String nicknameTxt, birthDayTxt;
    private int genderInt;

    String[] genders = {"Nam", "Nữ", "Khác"};

    private final ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        imageChoice = intent.getData();
                        Bitmap bitmapImage = null;
                        try {
                            bitmapImage = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageChoice);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (bitmapImage != null) {

                            avatar.setImageBitmap(bitmapImage);
                        }
                    }
                }
            });


    public UpdateInfoBottomSheetDialog(UserFragment uf) {
        userFragment = uf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }


    @Override
    public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();

        if (dialog != null) {
            dialog.setOnShowListener(dialog1 -> {
                FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                if (bottomSheet != null) {
                    BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    behavior.setDraggable(false);
                    behavior.setPeekHeight(0);
                    bottomSheet.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                    ViewCompat.setBackground(bottomSheet, getDrawable(requireContext(), R.drawable.rounded_dialog));
                }
            });
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CommonUtils.enableLightStatusBar(requireActivity());
        rootView = inflater.inflate(R.layout.modal_update_info, container, false);
        TextView choiceImgBtn = rootView.findViewById(R.id.choice_img_btn);
        MyTextView_Poppins_Medium cancelBtn = rootView.findViewById(R.id.btn_cancel);
        birthDayInput = rootView.findViewById(R.id.birthday_input);
        MyTextView_Poppins_Medium btnUpdate = rootView.findViewById(R.id.btn_update);

        birthDayInput.setOnClickListener(v -> showDatePickerDialog(birthDayInput));
        cancelBtn.setOnClickListener(v -> this.dismiss());
        choiceImgBtn.setOnClickListener(v -> startGallery());

        avatar = rootView.findViewById(R.id.user_avatar);
        genderInput = rootView.findViewById(R.id.gender_input);

        EditText_Poppins_Regular emailInput = rootView.findViewById(R.id.email_input);
        nicknameInput = rootView.findViewById(R.id.nickname_input);


        String emailTxt = (String) SharedPreferencesUtils.getParam(requireContext(), UC.EMAIL, "");
        String nicknameTxt = (String) SharedPreferencesUtils.getParam(requireContext(), UC.NICKNAME, "");
        String birthDayTxt = (String) SharedPreferencesUtils.getParam(requireContext(), UC.BIRTH_DAY, "");
        int genderInt = (int) SharedPreferencesUtils.getParam(requireContext(), UC.GENDER, 0);
        String avatarUrl = (String) SharedPreferencesUtils.getParam(requireContext(), UC.AVATAR, "");

        mBirthDayTxt = birthDayTxt;
        birthDayInput.setText(birthDayTxt);
        genderInput.setText(genderInt == 0 ? getString(R.string.boy_txt) : (genderInt == 1 ? getString(R.string.girl_txt) : getString(R.string.other_txt)));

        emailInput.setText(emailTxt);
        nicknameInput.setText(nicknameTxt);

        GlideApp.with(requireContext()).asBitmap().load(avatarUrl)
                .placeholder(CommonUtils.shimmerEffect())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, Transition<? super Bitmap> transition) {
                        avatar.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                    }
                });

        genderDropdown(rootView);

        btnUpdate.setOnClickListener(v -> changeProfile());
//        ProgressBar spinner = rootView.findViewById(R.id.loading_content);
//        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#4896f0"), android.graphics.PorterDuff.Mode.MULTIPLY);
        return rootView;
    }


    private void genderDropdown(View rootView) {
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = rootView.findViewById(R.id.gender_list);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genders);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) view).setText(null);

        String gender = genders[position];
        genderInput.setText(gender);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void showDatePickerDialog(EditText_Poppins_Regular birthDayInput) {
        DialogFragment newFragment = new DatePickerFragment(birthDayInput, mBirthDayTxt);
        newFragment.show(requireActivity().getSupportFragmentManager(), "datePicker");

    }

    private void startGallery() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mStartForResult.launch(i);
    }


    private void changeProfile() {

        ProfileModel profileModel = new ProfileModel();

        profileModel.setBirthday(birthDayInput.getText().toString());
        String genderText = genderInput.getText().toString();

        if (genderText.equals(getString(R.string.boy_txt))) {
            profileModel.setGender(0);
        }
        if (genderText.equals(getString(R.string.girl_txt))) {
            profileModel.setGender(1);
        }
        if (genderText.equals(getString(R.string.other_txt))) {
            profileModel.setGender(2);
        }
        profileModel.setNickname(nicknameInput.getText().toString());

        userFragment.changeInfo(profileModel, imageChoice);
    }

}