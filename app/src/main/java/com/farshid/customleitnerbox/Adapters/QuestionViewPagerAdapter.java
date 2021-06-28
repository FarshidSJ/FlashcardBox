package com.farshid.customleitnerbox.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.farshid.customleitnerbox.Fragments.CorrectsFragment;
import com.farshid.customleitnerbox.Fragments.QuestionFragment;
import com.farshid.customleitnerbox.Fragments.WrongsFragment;

public class QuestionViewPagerAdapter extends FragmentPagerAdapter {
    public QuestionViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new QuestionFragment(); //ChildFragment1 at position 0
            case 1:
                return new CorrectsFragment(); //ChildFragment2 at position 1
            case 2:
                return new WrongsFragment(); //ChildFragment3 at position 2
        }
        return null; //does not happen
    }

    @Override
    public int getCount() {
        return 3; //three fragments
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Questions";
            case 1:
                return "Corrects";
            case 2:
                return "Wrongs";

            default:
                return "";


        }
    }
}
