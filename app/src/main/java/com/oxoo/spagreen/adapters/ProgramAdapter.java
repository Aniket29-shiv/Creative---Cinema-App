package com.oxoo.spagreen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oxoo.spagreen.R;
import com.oxoo.spagreen.models.Program;
import com.oxoo.spagreen.utils.ItemAnimation;

import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {

    private List<Program> programs;
    private Context context;
    private OnProgramClickListener onProgramClickListener;

    private int lastPosition = -1;
    private boolean on_attach = true;
    private int animation_type = 2;

    public ProgramAdapter(List<Program> programs, Context context) {
        this.programs = programs;
        this.context = context;
    }

    public void setOnProgramClickListener(OnProgramClickListener onProgramClickListener) {
        this.onProgramClickListener = onProgramClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_program_item, parent,
                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Program program = programs.get(position);
        if (program != null) {

            holder.programTypeTv.setText(program.getTitle());
            holder.programTimeTv.setText(program.getTime());

        }

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onProgramClickListener != null) {
                    onProgramClickListener.onProgramClick(program);
                }
            }
        });

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return programs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView programTypeTv, programTimeTv;
        LinearLayout itemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            programTimeTv = itemView.findViewById(R.id.program_time_tv);
            programTypeTv = itemView.findViewById(R.id.program_type_tv);
            itemLayout = itemView.findViewById(R.id.item_layout);



        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }

        });

        super.onAttachedToRecyclerView(recyclerView);
    }

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
            lastPosition = position;
        }
    }

    public interface OnProgramClickListener{
        void onProgramClick(Program program);
    }

}
