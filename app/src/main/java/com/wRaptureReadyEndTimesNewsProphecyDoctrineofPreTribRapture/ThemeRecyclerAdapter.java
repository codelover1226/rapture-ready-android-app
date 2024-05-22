package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ThemeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context context;
    private final ArrayList<ThemeModel> colorPositions;
    private final ArrayList<Boolean> checkedArray;

    private int selected;
    private RecyclerView recyclerView;

    public ThemeRecyclerAdapter(Context context, int selected){
        this.context = context;
        this.selected = selected;

        colorPositions = new ArrayList<>(0);
        checkedArray = new ArrayList<>(0);

        for (int i = 0; i < ThemeModel.getSize(); i++){
            colorPositions.add(ThemeModel.get(i));
            checkedArray.add(false);
        }
        checkedArray.set(selected, true);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_views, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            final Holder holder1 = (Holder) holder;

            holder1.onCheckedChangeListener = (buttonView, isChecked) -> {
                if (isChecked && recyclerView != null){
                    recyclerView.post(() -> {
                        int adapterPosition = holder1.getAdapterPosition() == RecyclerView.NO_POSITION ? 0 : holder1.getAdapterPosition();
                        int prev = selected;
                        checkedArray.set(prev, false);
                        checkedArray.set(adapterPosition, true);
                        selected = adapterPosition;
                        notifyItemChanged(prev);
                    });
                }
            };

            holder1.statusBarColor.setBackgroundColor(colorPositions.get(position).STATUS_BAR_COLOR);
            holder1.toolbarColor.setBackgroundColor(colorPositions.get(position).TOOLBAR_COLOR);
            holder1.tabLayoutColor.setBackgroundColor(colorPositions.get(position).TAB_LAYOUT_BACKGROUND);
            holder1.switchButton.setChecked(checkedArray.get(position));

            if(position == 0)
                holder1.textView.setText(R.string.default_theme_value);
            else
                holder1.textView.setText(context.getResources().getString(R.string.theme_number, colorPositions.get(position).mapPosition));
        }
    }

    @Override
    public int getItemCount() {
        return colorPositions.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        /*if (!recyclerView.isComputingLayout()){

        }*/
    }

    public int getSelectedColorIndex(){
        return colorPositions.get(selected).mapPosition;
    }

    private static class Holder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        SwitchMaterial switchButton;
        MaterialTextView textView;
        AppCompatImageView statusBarColor;
        AppCompatImageView toolbarColor;
        AppCompatImageView tabLayoutColor;

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

        public Holder(@NonNull View itemView) {
            super(itemView);
            switchButton = itemView.findViewById(R.id.theme_switch_button);
            textView = itemView.findViewById(R.id.theme_text_view);
            toolbarColor = itemView.findViewById(R.id.theme_toolbar_color);
            statusBarColor = itemView.findViewById(R.id.theme_status_bar_color);
            tabLayoutColor = itemView.findViewById(R.id.theme_tab_layout_color);

            switchButton.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (onCheckedChangeListener != null)
                onCheckedChangeListener.onCheckedChanged(buttonView, isChecked);
        }
    }
}
