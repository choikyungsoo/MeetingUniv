package com.softcon.meetinguniv.main;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.softcon.meetinguniv.AddTeamElementFragment;
import com.softcon.meetinguniv.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class chooseteamElementFragment extends Fragment {
    private ImageButton backbtn;
    private EditText editSearch;
    private InputMethodManager imm;
//    private ArrayList<chooseteamRecycleritem> list = new ArrayList<chooseteamRecycleritem>();
    private List<chooseteamRecycleritem> list;
    private chooseteamAdapterRecyleritem recyclerItemAdapter;
    private LinearLayout addTeamLinear;
    private RecyclerView recyclerView;
    private ConstraintLayout chooseteamConstraint;

    private AddTeamElementFragment ATEfragment;
    private ArrayList<Integer> ImageSource = new ArrayList<Integer>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.recyclerView = view.findViewById(R.id.teamrecycler) ;

//        chooseteamAdapterRecyleritem recyclerItemAdapter = new chooseteamAdapterRecyleritem(this.list);
//        recyclerView.setAdapter(recyclerItemAdapter) ;

        this.list = new ArrayList<chooseteamRecycleritem>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem("명지대", "ㄱㄱ,ㄴㄴ,ㄷㄷ");
        addRecyclerItem("한양대", "1,2,3");
        addRecyclerItem("연세대", "A,B,c");
        addRecyclerItem("고려대", "가,나,다");
        addRecyclerItem("명지대", "ㄱㄱ,ㄴㄴ,ㄷㄷ");
        addRecyclerItem("한양대", "1,2,3");
        addRecyclerItem("연세대", "A,B,c");
        addRecyclerItem("고려대", "가,나,다");

        this.recyclerItemAdapter = new chooseteamAdapterRecyleritem(this.getActivity(), this.list);
        recyclerView.setAdapter(this.recyclerItemAdapter);

//        recyclerItemAdapter.notifyDataSetChanged();

        this.editSearch = (EditText) view.findViewById(R.id.searchTeamEditText);

        // 엔터 눌렀을 때 키보드 숨기기
        this.editSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( editSearch.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                recyclerItemAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        ClickHandler clickHandler = new ClickHandler();
        this.ATEfragment = new AddTeamElementFragment(clickHandler);
    }

    private void addRecyclerItem( String name, String member){
        chooseteamRecycleritem recyclerItem = new chooseteamRecycleritem();
        recyclerItem.setName(name);
        recyclerItem.setMember(member);
        list.add(recyclerItem);
        System.out.println("제발 좀 돼라"+name);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_chooseteam_element, container, false);
        this.backbtn = view.findViewById(R.id.backbtn);
        this.editSearch = view.findViewById(R.id.searchTeamEditText);
        this.imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.addTeamLinear = view.findViewById(R.id.addTeamLinear);
        this.chooseteamConstraint = view.findViewById(R.id.chooseteamConstraint);

        this.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchingContentFragment fragment1 = new MatchingContentFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Framecontainer, fragment1)
                        .commit();
                imm.hideSoftInputFromWindow(editSearch.getWindowToken(),0);
            }
        });
        this.addTeamLinear.setOnClickListener(new View.OnClickListener() {
//            AddTeamElementFragment addTeamElementFragment = new AddTeamElementFragment();
            @Override
            public void onClick(View v) {
                giveRecycleritemData();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.translate_up,R.anim.translate_up)
                        .replace(R.id.chooseteamFrame, ATEfragment)
                        .commit();
                imm.hideSoftInputFromWindow(editSearch.getWindowToken(),0);
            }
        });
        return view;
    }

    private void giveRecycleritemData() {
        Bundle bundle = getArguments();
//        bundle.putIntegerArrayList("currentteam", this.ImageSource);
        this.ATEfragment.setArguments(bundle);
    }

    public class ClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.addTeamOk_okBTN) {
                addRecyclerItem("추가됨", "ㄱㄱ,ㄴㄴ,ㄷㄷ");
                recyclerItemAdapter.notifyDataSetChanged();
                Log.d(TAG, "-------------------------dsfsfsf---------------------");
                v.getRootView().setVisibility(View.GONE);
            }
        }
    }
}