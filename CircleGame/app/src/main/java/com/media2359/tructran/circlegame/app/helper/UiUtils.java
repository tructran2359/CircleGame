package com.media2359.tructran.circlegame.app.helper;

import com.media2359.tructran.circlegame.app.model.ModelPoint;

/**
 * Created by TrucTran on 5/19/16.
 */
public class UiUtils {

    public static ModelPoint calculateCenterPoint(
            int radiusCenterOfArc,
            float angle,
            int radiusOfWholeView) {

        float x = (float)(radiusCenterOfArc * Math.cos((double) Utils.convertFromDegreeToRadian(angle))) + radiusOfWholeView;
        float y = (float)(radiusCenterOfArc * Math.sin((double) Utils.convertFromDegreeToRadian(angle))) + radiusOfWholeView;
        return new ModelPoint(x, y);
    }

}
