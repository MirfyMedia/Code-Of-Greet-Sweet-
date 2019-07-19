package com.greetsweet.plant.editor;

import com.greetsweet.plant.editor.BrushDrawingView;



interface BrushViewChangeListener {
    void onViewAdd(BrushDrawingView brushDrawingView);

    void onViewRemoved(BrushDrawingView brushDrawingView);

    void onStartDrawing();

    void onStopDrawing();
}
