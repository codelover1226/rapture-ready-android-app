package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A custom fullscreen color picker dialogFragment
 * this class extends DialogFragment, creates an AlertDialog that contains a custom Title and Custom Content View
 * it has buttons that either writes to preferences or cancels operations.
 */
public class ThemeDialogFragment extends DialogFragment {

    RecyclerView recyclerView;
    ThemeRecyclerAdapter adapter;

    public  ThemeDialogFragment(){

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View[] array = setUpView();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setCustomTitle(array[1]).setPositiveButton("Set", (d, which) -> {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(requireActivity().getApplicationContext()).edit();
            editor.putInt(ThemeModel.COLOR_KEY, adapter.getSelectedColorIndex());
            editor.apply();
        }).setNegativeButton("Cancel", (d, which) -> d.cancel()).setNeutralButton("Default", (d, which) -> {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(requireActivity().getApplicationContext()).edit();
            ThemeModel themeModel = ThemeModel.getDefault();
            editor.putInt(ThemeModel.COLOR_KEY, themeModel.mapPosition);
            editor.apply();
        }).setCancelable(false).setView(array[0]);
        return  builder.create();
    }

    /*
    * this method takes care of setting the dialog to become fullscreen, it retrieves the dialog,
    * get the dialog window and set it's layout
    * */
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null){
            int size = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(size, size);
        }
    }

    /*
    * custom views is inflated, set up and returned in this method
    * */
    @SuppressLint("InflateParams")
    private View[] setUpView(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireActivity().getApplicationContext());
        Integer colorPosition = preferences.getInt(ThemeModel.COLOR_KEY, 0);
        ThemeModel themeModel = ThemeModel.get(colorPosition);

        adapter = new ThemeRecyclerAdapter(requireContext(), themeModel.mapPosition);

        View customLayout = getLayoutInflater().inflate(R.layout.theme_custom_dialog_view, null);
        recyclerView = customLayout.findViewById(R.id.dialog_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        View customTitle = getLayoutInflater().inflate(R.layout.custom_dialog_title_view, null);
        customTitle.setBackgroundColor(themeModel.TAB_LAYOUT_BACKGROUND);

        recyclerView.setAdapter(adapter);
        return new View[]{customLayout, customTitle};
    }
}
