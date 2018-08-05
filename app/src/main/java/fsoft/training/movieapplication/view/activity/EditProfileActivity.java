package fsoft.training.movieapplication.view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import fsoft.training.movieapplication.R;
import fsoft.training.movieapplication.application.utils.AppSingleton;
import fsoft.training.movieapplication.application.utils.RoundedTransformation;
import fsoft.training.movieapplication.constant.Constants;

/**
 * Created by ManhND16 on 10/27/2017
 */
public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageviewAvatar;
    private Button mBtnDone;
    private Button mBtnCancel;
    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private RadioGroup mRadioGroup;

    private String mNname;
    private String mEmail;
    private String mBirthDay;
    private String mGender;
    private String mProfilePicture;
    private TextView mBirthdayTv;
    private TextView mOldEmail;

    private SharedPreferences mSharedPreferences;
    private Bitmap mAvatarGallery;
    private Bitmap mAvatarCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mImageviewAvatar = (ImageView) findViewById(R.id.image_avatar_edit_profile);
        mBtnDone = (Button) findViewById(R.id.done_edit_profile_button);
        mBtnCancel = (Button) findViewById(R.id.cancel_edit_profile_button);
        mBirthdayTv = (TextView) findViewById(R.id.birthday_editprofile_textview);
        mEditTextEmail = (EditText) findViewById(R.id.email_editprofile_textview);
        mEditTextName = (EditText) findViewById(R.id.name_editprofile_edittext);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group_gender);
        mOldEmail = (TextView) findViewById(R.id.old_email_editprofile_textview);
        Bundle bundle = getIntent().getExtras();
        mEditTextName.setText(bundle.getString(Constants.EDIT_PROFILE_BUNDLE_KEY_NAME));
        mEditTextEmail.setText(bundle.getString(Constants.EDIT_PROFILE_BUNDLE_KEY_EMAIL));
        mOldEmail.setText(bundle.getString(Constants.EDIT_PROFILE_BUNDLE_KEY_EMAIL));
        mBirthdayTv.setText(bundle.getString(Constants.EDIT_PROFILE_BUNDLE_KEY_BIRTHDAY));
        if (Constants.GENDER_MALE.equals(bundle.getString(Constants.EDIT_PROFILE_BUNDLE_KEY_GENDER))) {
            mRadioGroup.check(R.id.male_radio_button);
        } else {
            mRadioGroup.check(R.id.female_radio_button);
        }
        mSharedPreferences = getSharedPreferences(Constants.PROFILE_NAME, MODE_PRIVATE);
        String avatar = mSharedPreferences.getString("profile_picture", "");
        Bitmap savingAvatar = AppSingleton.decodeBase64(avatar);
        if (savingAvatar != null) {
            mImageviewAvatar.setImageBitmap(savingAvatar);
        }
        mBtnDone.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mImageviewAvatar.setOnClickListener(this);
        mBirthdayTv.setOnClickListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater()
                .inflate(R.menu.menu_avatar, menu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_avatar_edit_profile:
                registerForContextMenu(mImageviewAvatar);
                openContextMenu(view);
                break;
            case R.id.done_edit_profile_button:
                setResult(RESULT_OK);
                mSharedPreferences = getSharedPreferences(Constants.PROFILE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                if (mAvatarGallery != null) {
                    Log.d("EditProfileActivity", "onClick: " + AppSingleton.encodeTobase64(mAvatarGallery));
                    editor.putString(Constants.EDIT_PROFILE_PREFERENCES_KEY_PICTURE_PROFILE
                            , AppSingleton.encodeTobase64(mAvatarGallery));
                } else if (mAvatarCamera != null) {
                    editor.putString(Constants.EDIT_PROFILE_PREFERENCES_KEY_PICTURE_PROFILE
                            , AppSingleton.encodeTobase64(mAvatarCamera));
                }
                editor.putString(Constants.EDIT_PROFILE_PREFERENCES_KEY_NAME, mEditTextName
                        .getText().toString());
                editor.putString(Constants.EDIT_PROFILE_PREFERENCES_KEY_EMAIL, mEditTextEmail
                        .getText().toString());
                editor.putString(Constants.EDIT_PROFILE_PREFERENCES_KEY_BIRTHDAY, mBirthdayTv
                        .getText().toString());
                String radiovalue = ((RadioButton) findViewById(mRadioGroup.getCheckedRadioButtonId()))
                        .getText().toString();
                editor.putString(Constants.EDIT_PROFILE_PREFERENCES_KEY_GENDER, radiovalue);
                editor.commit();
                finish();
                break;
            case R.id.cancel_edit_profile_button:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.birthday_editprofile_textview:
                chooseBirthday();
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera_avatar:
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(captureIntent, Constants.REQUEST_CODE_CAMERA);
                break;
            case R.id.gallery_avatar:
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, Constants.REQUEST_CODE_GALLERY);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            RoundedTransformation mPicassoCircleTransformation = new RoundedTransformation();
            mAvatarCamera = mPicassoCircleTransformation.transform(bitmap);
            mImageviewAvatar.setImageBitmap(mAvatarCamera);
        }
        if (requestCode == Constants.REQUEST_CODE_GALLERY && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmapGallery = BitmapFactory.decodeFile(picturePath);
            RoundedTransformation roundedTransformation = new RoundedTransformation();
            mAvatarGallery = roundedTransformation.transform(bitmapGallery);
            mImageviewAvatar.setImageBitmap(mAvatarGallery);

        }
    }

    /**
     * choose birthday in editprofile screen
     */
    private void chooseBirthday() {
        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DATE);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);
        final Calendar current = Calendar.getInstance();
        current.set(year, month, day);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(i, i1, i2);
                mBirthDay = simpleDateFormat.format(calendar.getTime());
                if (current.after(calendar)) {
                    mBirthdayTv.setText(mBirthDay);
                } else {
                    Toast.makeText(EditProfileActivity.this, Constants.BIRTHDAY_CHOOSE_ERROR, Toast.LENGTH_SHORT).show();

                }


            }
        }, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        calendar.set(year, month, day);
        SimpleDateFormat simpleDateFormatTitle = new SimpleDateFormat("EEE, MMM dd, yyyy");
        datePickerDialog.setTitle(simpleDateFormatTitle.format(calendar.getTime()));
        datePickerDialog.show();
    }

}
