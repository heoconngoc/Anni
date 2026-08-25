package com.dat.anni.gui;

/**
 * Panel có tham chiếu tới MainPanel để điều hướng. MainPanel tự nối tham chiếu
 * khi đăng ký panel — mọi panel mới bắt buộc implement interface này.
 */
public interface MainPanelAware {

	void setMainPanel(MainPanel main);
}
