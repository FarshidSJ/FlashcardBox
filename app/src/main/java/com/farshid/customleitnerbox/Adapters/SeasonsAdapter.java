package com.farshid.customleitnerbox.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.farshid.customleitnerbox.Database.LeitnerEntity;
import com.farshid.customleitnerbox.R;

public class SeasonsAdapter extends ListAdapter<LeitnerEntity, SeasonsAdapter.SeasonsViewHolder> {
    private Context context;
    public OnItemClickListener listener;
    private static final String TAG = "SeasonsAdapter";
    public SeasonsAdapter(Context context/*, List<LeitnerEntity> leitnerEntities*/) {
        super(DIFF_CALLBACK);
        this.context = context;
//        this.leitnerEntities = leitnerEntities;
    }

    public static DiffUtil.ItemCallback<LeitnerEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<LeitnerEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull LeitnerEntity oldItem, @NonNull LeitnerEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull LeitnerEntity oldItem, @NonNull LeitnerEntity newItem) {
            return oldItem.getSeasonName().equals(newItem.getSeasonName()) &&
                    oldItem.getSeasonId() == newItem.getSeasonId();

        }

    };

    @NonNull
    @Override
    public SeasonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.season_layout, parent, false);
        return new SeasonsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonsViewHolder holder, int position) {
        final LeitnerEntity leitnerEntity = getItem(position);
        holder.seasonNumber.setText(String.valueOf(leitnerEntity.getSeasonId()));
//        Log.i(TAG, "onBindViewHolder: " + leitnerEntity.getSeasonName());
        holder.seasonName.setText(leitnerEntity.getSeasonName());
    }

    public class SeasonsViewHolder extends RecyclerView.ViewHolder{
        public TextView seasonNumber, seasonName;

        public SeasonsViewHolder(@NonNull View itemView) {
            super(itemView);
            seasonNumber = itemView.findViewById(R.id.txt_season_number);
            seasonName = itemView.findViewById(R.id.txt_season_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(LeitnerEntity leitnerEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
