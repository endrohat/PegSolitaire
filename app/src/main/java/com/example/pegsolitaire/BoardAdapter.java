package com.example.pegsolitaire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ImageHolder> {
    public BoardItem[] getStates() {
        return states;
    }

    private BoardItem[] states;
    private final LayoutInflater m_layoutInflater;
    private final MainActivity m_activity;

    public void setImageId(int imageId) {
        this.imageId = imageId;
        notifyDataSetChanged();
    }

    private int imageId = R.drawable.tentacles;

    public void setStates(BoardItem[] states) {
        this.states = states;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    int selectedIndex = -1;

    public void setTotalHeight(int totalHeight) {
        this.totalHeight = totalHeight;
    }

    int totalHeight;

    public BoardAdapter(MainActivity activity, BoardItem[] states, int totalHeight) {
        m_layoutInflater = LayoutInflater.from(activity);
        m_activity = activity;
        this.states = states;
        this.totalHeight = totalHeight;

    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ImageHolder holder = new ImageHolder((View)m_layoutInflater.inflate(R.layout.peg_layout, parent, false));
        holder.m_imageView.setImageResource(imageId);
        //holder.itemView.setLayoutH
        return holder;
    }

    @Override
    public void onViewRecycled(ImageHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        boolean isPeg = states[position].isPeg();
        boolean isSelected = position == selectedIndex;

        holder.m_imageView.getLayoutParams().height = totalHeight/4;


        if(isPeg) {
            holder.m_imageView.setImageResource(imageId);
        } else {
            holder.m_imageView.setImageResource(R.drawable.rectangle);
        }

        if(isSelected) {
            holder.m_imageView.setBackgroundResource(R.drawable.image_border);
        } else {
            holder.m_imageView.setBackgroundResource(0);
        }

    }

    @Override
    public int getItemCount() {
        return states.length;
    }

    public class ImageHolder extends RecyclerView.ViewHolder {

        ImageView m_imageView ;
        ImageHolder (View itemView) {
            super(itemView);
            m_imageView = itemView.findViewById(R.id.imageView);

            m_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeMove(getAdapterPosition());
                }
            });
        }
    }

    public void updateResults( BoardItem[] states) {
        this.states = states;
        notifyDataSetChanged();
    }

    private void makeMove(int adapterPosition) {
        boolean isPeg = states[adapterPosition].isPeg();
        if(isPeg) {
            int previousIndex = selectedIndex;
            selectedIndex = adapterPosition;
            notifyItemChanged(selectedIndex);
            notifyItemChanged(previousIndex);
            return;
        }

        if(canPlayLeft(adapterPosition, selectedIndex)) {
            states[adapterPosition].setPeg(true);
            states[selectedIndex].setPeg(false);
            states[selectedIndex - 1].setPeg(false);
            selectedIndex = -1;
            notifyDataSetChanged();
            if(checkWin()) {
                m_activity.gameStatus.setText("You win!");
                Toast.makeText(m_activity, "You win!", Toast.LENGTH_SHORT).show();
            } else if(checkLose()) {
                m_activity.gameStatus.setText("You Lose!");
                Toast.makeText(m_activity, "You Lose!", Toast.LENGTH_SHORT).show();
            }

            return;
        }

        if(canPlayRight(adapterPosition, selectedIndex)) {
            states[adapterPosition].setPeg(true);
            states[selectedIndex].setPeg(false);
            states[selectedIndex + 1].setPeg(false);
            selectedIndex = -1;
            notifyDataSetChanged();
            if(checkWin()) {
                m_activity.gameStatus.setText("You win!");
                Toast.makeText(m_activity, "You win!", Toast.LENGTH_SHORT).show();
            } else if(checkLose()) {
                m_activity.gameStatus.setText("You Lose!");
                Toast.makeText(m_activity, "You Lose!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        if(canPlayUp(adapterPosition, selectedIndex)) {
            states[adapterPosition].setPeg(true);
            states[selectedIndex].setPeg(false);
            states[selectedIndex - 4].setPeg(false);
            selectedIndex = -1;
            notifyDataSetChanged();
            if(checkWin()) {
                m_activity.gameStatus.setText("You win!");
                Toast.makeText(m_activity, "You win!", Toast.LENGTH_SHORT).show();
            } else if(checkLose()) {
                m_activity.gameStatus.setText("You Lose!");
                Toast.makeText(m_activity, "You Lose!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        if(canPlayDown(adapterPosition, selectedIndex)) {
            states[adapterPosition].setPeg(true);
            states[selectedIndex].setPeg(false);
            states[selectedIndex + 4].setPeg(false);
            selectedIndex = -1;
            notifyDataSetChanged();
            if(checkWin()) {
                m_activity.gameStatus.setText("You win!");
                Toast.makeText(m_activity, "You win!", Toast.LENGTH_SHORT).show();
            } else if(checkLose()) {
                m_activity.gameStatus.setText("You Lose!");
                Toast.makeText(m_activity, "You Lose!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    private boolean checkWin() {
        int pegs = 0;
        for( BoardItem item : states) {
            if(item.isPeg) {
                pegs++;
            }

            if(pegs >1 ) {
                return false;
            }
        }

        return true;
    }

    private boolean checkLose() {
        int i=0;
        for( BoardItem item : states) {
            if(!item.isPeg) {
                i++;
                continue;
            }


            if(canPlayLeft(i - 2, i)) {
                return false;
            }

            if(canPlayRight(i + 2, i)) {
                return false;
            }

            if(canPlayUp(i - 8, i)) {
                return false;
            }

            if(canPlayDown(i + 8, i)) {
                return false;
            }

            i++;
        }

        return true;
    }

    private boolean canPlayLeft(int adapterPosition, int selectedIndex) {

        if(adapterPosition<0 || adapterPosition >= states.length || states[adapterPosition].isPeg) {
            return false;
        }

        if(selectedIndex % 4 == 2 && adapterPosition % 4 == 0 && states[selectedIndex - 1].isPeg()) {
            return true;
        }

        if(selectedIndex % 4 == 3 && adapterPosition % 4 == 1 && states[selectedIndex - 1].isPeg()) {
            return true;
        }

        return false;
    }

    private boolean canPlayRight(int adapterPosition, int selectedIndex) {
        if(adapterPosition<0 || adapterPosition >= states.length || states[adapterPosition].isPeg) {
            return false;
        }

        if(selectedIndex % 4 == 0 && adapterPosition % 4 == 2 && states[selectedIndex + 1].isPeg()) {
            return true;
        }

        if(selectedIndex % 4 == 1 && adapterPosition % 4 == 3 && states[selectedIndex + 1].isPeg()) {
            return true;
        }

        return false;
    }

    private boolean canPlayUp(int adapterPosition, int selectedIndex) {

        if(adapterPosition<0 || adapterPosition >= states.length || states[adapterPosition].isPeg) {
            return false;
        }

        if(selectedIndex > 7  && (selectedIndex - adapterPosition)  ==  8  && states[selectedIndex - 4].isPeg()) {
            return true;
        }

        return false;
    }

    private boolean canPlayDown(int adapterPosition , int selectedIndex) {

        if(adapterPosition<0 || adapterPosition >= states.length || states[adapterPosition].isPeg) {
            return false;
        }

        if(selectedIndex < 8  && ( adapterPosition - selectedIndex)  ==  8  && states[selectedIndex + 4].isPeg()) {
            return true;
        }


        return false;
    }
}
