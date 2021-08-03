package com.example.meetinguniv;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class ChangePersonalProfileImageDialog  extends Fragment implements View.OnClickListener {
    private Context context;
    private ImageView personal_profile_image;
    private Button changeProfileImage_cameraBTN, changeProfileImage_albumBTN, changePersonalProfileImage_cancelBTN;
    final private static String TAG = "GILBOMI"; Button btn_photo; ImageView iv_photo; final static int TAKE_PICTURE = 1; String mCurrentPhotoPath; final static int REQUEST_TAKE_PHOTO = 1;

    public ChangePersonalProfileImageDialog(Context context) {
        this.context = context;
    }

    public void changeProfileImageFunction(ImageView personal_profile_image) {
        this.personal_profile_image = personal_profile_image;
        Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog_change_personal_profile_image);
        dlg.show();

        this.changeProfileImage_cameraBTN = dlg.findViewById(R.id.changeProfileImage_cameraBTN);
        this.changeProfileImage_albumBTN = dlg.findViewById(R.id.changeProfileImage_albumBTN);
        this.changePersonalProfileImage_cancelBTN = dlg.findViewById(R.id.changePersonalProfileImage_cancelBTN);

        this.changeProfileImage_cameraBTN.setOnClickListener(this);
        this.changeProfileImage_albumBTN.setOnClickListener(this);
        this.changePersonalProfileImage_cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeProfileImage_cameraBTN:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
                break;
            case R.id.changeProfileImage_albumBTN:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 200);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            this.personal_profile_image.setImageURI(selectedImageUri);
        } else if (requestCode == TAKE_PICTURE) {
            if (resultCode == RESULT_OK && data.hasExtra("data")) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) { this.personal_profile_image.setImageBitmap(bitmap);
                }
            }
        }
    }
}
