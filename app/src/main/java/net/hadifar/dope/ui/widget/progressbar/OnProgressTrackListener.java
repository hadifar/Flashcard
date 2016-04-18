package net.hadifar.dope.ui.widget.progressbar;

public interface OnProgressTrackListener {
    public void onProgressFinish();

    public void onProgressUpdate(int progress);
}