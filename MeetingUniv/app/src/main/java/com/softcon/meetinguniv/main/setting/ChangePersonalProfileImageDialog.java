package com.softcon.meetinguniv.main.setting;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.main.PersonalProfileScreenFragment;

public class ChangePersonalProfileImageDialog extends Fragment {
    private Context context;
    private Dialog dlg;
    private ImageView personal_profile_image;
    private Button changeProfileImage_cameraBTN, changeProfileImage_albumBTN, changePersonalProfileImage_cancelBTN;
    String TAG = "GILBOMI";
    Button btn_photo;
    ImageView iv_photo;
    int TAKE_PICTURE = 1;
    String mCurrentPhotoPath;
    int REQUEST_TAKE_PHOTO = 1;
    int num = 0;

    public ChangePersonalProfileImageDialog(Context context) {
        this.context = context;
    }

    public View getCameraBTN() {
        return this.changeProfileImage_cameraBTN;
    }

    public View getAlbumBTN() {
        return this.changeProfileImage_cameraBTN;
    }

    public View getCancelBTN() {
        return this.changeProfileImage_cameraBTN;
    }

    public Dialog getDlg() {
        return this.dlg;
    }

    public void dismissDlg() {
        this.dlg.dismiss();
    }

    public void changeProfileImageFunction(PersonalProfileScreenFragment.ClickHandler clickHandler) {
//        this.personal_profile_image = personal_profile_image;
        this.dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog_change_personal_profile_image);
        dlg.show();

        this.changeProfileImage_cameraBTN = dlg.findViewById(R.id.changeProfileImage_cameraBTN);
        this.changeProfileImage_albumBTN = dlg.findViewById(R.id.changeProfileImage_albumBTN);
        this.changePersonalProfileImage_cancelBTN = dlg.findViewById(R.id.changePersonalProfileImage_cancelBTN);

        this.changeProfileImage_cameraBTN.setOnClickListener(clickHandler);
        this.changeProfileImage_albumBTN.setOnClickListener(clickHandler);
        this.changePersonalProfileImage_cancelBTN.setOnClickListener(clickHandler);

//        this.changeProfileImage_cameraBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dlg.dismiss();
//                num = 1;
//                setEventNum(num);
////                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(cameraIntent, TAKE_PICTURE);
//            }
//        });
//        this.changeProfileImage_albumBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dlg.dismiss();
//                num = 2;
//                setEventNum(num);
////                Intent intent = new Intent(Intent.ACTION_PICK);
////                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
////                startActivityForResult(intent, 200);
//            }
//        });
//        this.changePersonalProfileImage_cancelBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dlg.dismiss();
//                num = 0;
//            }
//        });
    }

    public void setEventNum(int eventNum) {
//        PersonalProfileScreenFragment personalProfileScreenFragment = new PersonalProfileScreenFragment();
//        personalProfileScreenFragment.changeProfileImage(eventNum);
    }

//    public int getInt() {
//        this.personal_profile_image = personal_profile_image;
//        Dialog dlg = new Dialog(context);
//        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dlg.setContentView(R.layout.dialog_change_personal_profile_image);
//        dlg.show();
//
//        this.changeProfileImage_cameraBTN = dlg.findViewById(R.id.changeProfileImage_cameraBTN);
//        this.changeProfileImage_albumBTN = dlg.findViewById(R.id.changeProfileImage_albumBTN);
//        this.changePersonalProfileImage_cancelBTN = dlg.findViewById(R.id.changePersonalProfileImage_cancelBTN);
//
//        this.changeProfileImage_cameraBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dlg.dismiss();
//                num = 1;
////                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(cameraIntent, TAKE_PICTURE);
//
//            }
//        });
//        this.changeProfileImage_albumBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dlg.dismiss();
//                num = 2;
////                Intent intent = new Intent(Intent.ACTION_PICK);
////                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
////                startActivityForResult(intent, 200);
//            }
//        });
//        this.changePersonalProfileImage_cancelBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dlg.dismiss();
//            }
//        });
//        return num;
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.changeProfileImage_cameraBTN:
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, TAKE_PICTURE);
//                break;
//            case R.id.changeProfileImage_albumBTN:
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                startActivityForResult(intent, 200);
//                break;
//        }
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 200 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
//            Uri selectedImageUri = data.getData();
//            this.personal_profile_image.setImageURI(selectedImageUri);
//        } else if (requestCode == TAKE_PICTURE) {
//            if (resultCode == RESULT_OK && data.hasExtra("data")) {
//                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                if (bitmap != null) {
//                    this.personal_profile_image.setImageBitmap(bitmap);
//                }
//            }
//        }
//    }
}