package healerkart.com.dosage.Alpha;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
//Use Alarm Manager


public class dosageFrag extends Fragment {

    public dosageFrag()
    {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Intent firstpage= new Intent(getActivity(),AddDosage.class);
        getActivity().startActivity(firstpage);
        super.onCreate(savedInstanceState);
    }
}