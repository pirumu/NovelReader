package com.myproject.novel.ui.user;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.GlideApp;
import com.myproject.novel.local.util.SharedPreferencesUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.local.util.customfonts.MyTextView_Poppins_Medium;
import com.myproject.novel.model.ProfileModel;
import com.myproject.novel.ui.auth.AuthActivity;
import com.myproject.novel.ui.main.CallbackMainActivity;
import com.myproject.novel.ui.main.MainActivity;
import com.myproject.novel.ui.user.updateInfo.UpdateInfoBottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("deprecation")
public class UserFragment extends Fragment {

    private CallbackMainActivity mCallback;
    private UserViewModel mViewModel;
    private View rootView;
    private ActionBar actionBar;
    private UpdateInfoBottomSheetDialog updateInfoBottomSheetDialog;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.user_fragment, container, false);

        actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        MyTextView_Poppins_Medium logoutBtn = rootView.findViewById(R.id.logout_btn);
        MyTextView_Poppins_Medium updateInfoBtn = rootView.findViewById(R.id.update_info_btn);

        updateInfoBtn.setOnClickListener(v -> {
            updateInfoBottomSheetDialog = new UpdateInfoBottomSheetDialog(this);
            updateInfoBottomSheetDialog.show(getParentFragmentManager(), "updateInfoBottomSheetDialog");
        });
        logoutBtn.setOnClickListener(v -> confirmLogout());


        loadDataUser(rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mViewModel.profileModel.observe(getViewLifecycleOwner(), res -> {
            updateInfoBottomSheetDialog.dismiss();
        });
    }

    @Override
    public void onAttach(@NotNull Context context) {
        boolean isLogin = (boolean) SharedPreferencesUtils.getParam(requireContext(), UC.IS_USER_LOGGED_IN, false);
        super.onAttach(context);

        if (context instanceof CallbackMainActivity) {
            mCallback = (CallbackMainActivity) context;
            if (!isLogin) {
                SharedPreferencesUtils.setParam(requireContext(), UC.ACTIVITY_BEFORE, "MainActivity");
                mCallback.startActivity(AuthActivity.class, null);
            }
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackMainActivity");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (actionBar != null) {
            actionBar.hide();
        }
        loadDataUser(rootView);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (actionBar != null) {
            actionBar.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (actionBar != null) {
            actionBar.show();
        }
    }

    private void confirmLogout() {
        new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.logout_txt))
                .setMessage(R.string.confirm_logout_txt)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> logout())
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void logout() {
        SharedPreferencesUtils.setParam(requireContext(), UC.ACCESS_TOKEN, "");
        SharedPreferencesUtils.setParam(requireContext(), UC.IS_USER_LOGGED_IN, false);
        CommonUtils.startActivity(requireActivity(), MainActivity.class, null);
    }


    private void loadDataUser(View rootView) {
        TextView nickname = rootView.findViewById(R.id.nickname);
        TextView email = rootView.findViewById(R.id.email);
        TextView gender = rootView.findViewById(R.id.gender);
        TextView birthDay = rootView.findViewById(R.id.birthday);
        CircleImageView avatar = rootView.findViewById(R.id.user_avatar);


        String emailTxt = (String) SharedPreferencesUtils.getParam(requireContext(), UC.EMAIL, "");
        String nicknameTxt = (String) SharedPreferencesUtils.getParam(requireContext(), UC.NICKNAME, "");
        String birthDayTxt = (String) SharedPreferencesUtils.getParam(requireContext(), UC.BIRTH_DAY, "");
        int genderInt = (int) SharedPreferencesUtils.getParam(requireContext(), UC.GENDER, 0);
        String avatarUrl = (String) SharedPreferencesUtils.getParam(requireContext(), UC.AVATAR, "");

        nickname.setText(nicknameTxt);
        email.setText(emailTxt);
        gender.setText(genderInt == 0 ? getString(R.string.boy_txt) : (genderInt == 1 ? getString(R.string.girl_txt) : getString(R.string.other_txt)));
        birthDay.setText(birthDayTxt);


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

    }


    public void changeInfo(ProfileModel profileModel, Uri imageChoice) {
        String accessToken = (String) SharedPreferencesUtils.getParam(requireContext(), UC.ACCESS_TOKEN, "");
        if (imageChoice != null) {

            String realPath = createCopyAndReturnRealPath(requireContext(), imageChoice);

            mViewModel.updateInfo(profileModel, realPath, accessToken);

        } else {
            mViewModel.updateInfo(profileModel, accessToken);
        }
        mViewModel.profileModel.observe(getViewLifecycleOwner(), res -> {
            if (res != null) {
                SharedPreferencesUtils.setParam(requireContext(), UC.NICKNAME, res.getNickname());
                SharedPreferencesUtils.setParam(requireContext(), UC.BIRTH_DAY, res.getBirthday());
                SharedPreferencesUtils.setParam(requireContext(), UC.GENDER, res.getGender());
                SharedPreferencesUtils.setParam(requireContext(), UC.AVATAR, res.getAvatar());

                loadDataUser(rootView);
            }
        });
    }


    @Nullable
    public String createCopyAndReturnRealPath(
            @NonNull Context context, @NonNull Uri uri) {
        final ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null)
            return null;

        // Create file path inside app's data dir
        String filePath = context.getApplicationInfo().dataDir + File.separator
                + System.currentTimeMillis();

        File file = new File(filePath);
        try {
            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null)
                return null;

            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                outputStream.write(buf, 0, len);

            outputStream.close();
            inputStream.close();
        } catch (IOException ignore) {
            return null;
        }

        return file.getAbsolutePath();
    }
}