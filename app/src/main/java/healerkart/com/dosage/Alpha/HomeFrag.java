package healerkart.com.dosage.Alpha;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * Created by f3n1Xx on 29/08/15.
 */
public class HomeFrag extends Fragment {
    public HomeFrag()
    {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent firstpage= new Intent(getActivity(),Home.class);
        getActivity().startActivity(firstpage);
        super.onCreate(savedInstanceState);
    }

}
