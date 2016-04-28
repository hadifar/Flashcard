//package net.hadifar.dope.ui.fragment;
//
//import android.os.Bundle;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import net.hadifar.dope.R;
//import net.hadifar.dope.ui.adapter.ContentAdapter;
//
///**
// * Amir Hadifar on 29/07/2015
// * Cardy
// * Email : Hadifar.amir@gmail.com
// * Twitter : @AmirHadifar
// */
//
//public class FragmentFlashCardViewer extends BaseFragment {
//
//    private int selectedDockId = 0;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        return inflater.inflate(R.layout.activity_flashcard_viewer, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//
//        getBundle();
//
//        setupViewPager(view);
//
//    }
//
//    private void getBundle() {
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            selectedDockId = bundle.getInt(EXTRA_ID);
//            setTitle(bundle.getString(EXTRA_TITLE));
//        }
//    }
//
//    private void setupViewPager(View view) {
//        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.pager);
//        ContentAdapter mSectionsPagerAdapter = new ContentAdapter(getFragmentManager(), selectedDockId);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//
//    }
//}
