package healerkart.com.dosage.Alpha;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import healerkart.com.dosage.R;

/**
 * Created by f3n1Xx on 29/08/15.
 */
public class HomeFrag extends Fragment {
    public HomeFrag()
    {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home, container, false);

        return rootView;
    }
}
