package cloudlink.utility;

import cloudlink.model.FinderItem;

import java.util.Comparator;

public class FinderItemComparator implements Comparator<FinderItem> {


    @Override
    public int compare(FinderItem o1, FinderItem o2) {
        if(o1.getParents().size() >= o2.getParents().size()){
            return 1;
        }
        return 0;
    }
}
