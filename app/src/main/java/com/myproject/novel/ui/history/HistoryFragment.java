package com.myproject.novel.ui.history;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.SharedPreferencesUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.model.ListNovelModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.history.epoxy.HistoryController;
import com.myproject.novel.ui.novel.NovelActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class HistoryFragment extends Fragment implements HistoryController.EpoxyAdapterCallbacks {

    private HistoryViewModel mViewModel;
    private HistoryController historyController;
    private View rootView;
    private String jsonList;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.history_fragment, container, false);
        setController(requireContext());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        getData();

    }

    private void setController(Context ctx) {
        if (ctx != null) {
            historyController = new HistoryController(this);
            historyController.setDebugLoggingEnabled(true);
            RecyclerView historyRecyclerView = rootView.findViewById(R.id.history_list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
            historyRecyclerView.setLayoutManager(layoutManager);
            historyRecyclerView.setAdapter(historyController.getAdapter());
        }

    }


    @Override
    public void novelTitleClick(NovelModel model) {
        HashMap<String, String> data = new HashMap<>();
        data.put(UC.NOVEL_ID, String.valueOf(model.getNovelId()));
        CommonUtils.startActivity(requireActivity(), NovelActivity.class, data);
    }

    private void getData() {

        Type dataType = new TypeToken<ArrayList<NovelModel>>() {
        }.getType();

        jsonList = (String) SharedPreferencesUtils.getParam(requireContext(), UC.JUST_WATCHED, "");

        ListNovelModel listNovelModel = new ListNovelModel();

        if (!jsonList.equals("")) {
            Gson gson = new Gson();
            ArrayList<NovelModel> arrayList = gson.fromJson(jsonList, dataType);
            listNovelModel.setData(arrayList);
        }
        historyController.setData(listNovelModel);

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}