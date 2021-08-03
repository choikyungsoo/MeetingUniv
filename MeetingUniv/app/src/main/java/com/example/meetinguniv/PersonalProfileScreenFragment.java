package com.example.meetinguniv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalProfileScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalProfileScreenFragment extends Fragment implements View.OnClickListener {
    private ImageView personal_profile_image;
    private Button backToMainFromPersonalProfile_BTN;
    private TextView personal_name;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PersonalProfileScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalProfileScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalProfileScreenFragment newInstance(String param1, String param2) {
        PersonalProfileScreenFragment fragment = new PersonalProfileScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_profile_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.personal_profile_image = view.findViewById(R.id.personal_profile_image);
        this.personal_profile_image.setOnClickListener(this);

        this.backToMainFromPersonalProfile_BTN = view.findViewById(R.id.backToMainFromPersonalProfile_BTN);
        this.backToMainFromPersonalProfile_BTN.setOnClickListener(this);

        this.personal_name = view.findViewById(R.id.personal_name);
        this.personal_name.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_profile_image:
                ChangePersonalProfileImageDialog changePersonalProfileImageDialog = new ChangePersonalProfileImageDialog(getActivity());
                changePersonalProfileImageDialog.changeProfileImageFunction(this.personal_profile_image);
                break;
            case R.id.backToMainFromPersonalProfile_BTN:
                Navigation.findNavController(v).navigate(R.id.action_personalProfileScreenFragment_to_mainFragment);
                break;
            case R.id.personal_name:
                ChangePersonalNameDialog changePersonalNameDialog = new ChangePersonalNameDialog(getActivity());
                changePersonalNameDialog.changeNameFunction(this.personal_name);
                break;
        }
    }

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