package healerkart.com.dosage.Alpha;
//Create a ListFragment
//Use Recycler View if needed

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * Created by f3n1Xx on 29/08/15.
 */
public class HomeFrag extends ListFragment {
    public HomeFrag()
    {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Intent firstpage= new Intent(getActivity(),Home.class);
        getActivity().startActivity(firstpage);
        super.onCreate(savedInstanceState);
    }

}
