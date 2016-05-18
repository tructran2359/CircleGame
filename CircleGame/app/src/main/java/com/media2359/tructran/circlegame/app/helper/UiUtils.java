package com.media2359.tructran.circlegame.app.helper;

import com.media2359.tructran.circlegame.app.model.ModelPoint;

/**
 * Created by TrucTran on 5/19/16.
 */
public class UiUtils {

    public static ModelPoint calculateCenterPoint(
            int distanceFromCenterOfContainerViewToCenterOfCircle,
            float angle,
            int distanceFromLeftToCenterOfContainerView) {

        float x = (float)(distanceFromCenterOfContainerViewToCenterOfCircle * Math.cos((double) Utils.convertFromDegreeToRadian(angle))) + distanceFromLeftToCenterOfContainerView;
        float y = (float)(distanceFromCenterOfContainerViewToCenterOfCircle * Math.sin((double) Utils.convertFromDegreeToRadian(angle))) + distanceFromLeftToCenterOfContainerView;
        return new ModelPoint(x, y);
    }

}
