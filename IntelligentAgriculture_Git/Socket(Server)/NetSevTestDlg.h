// NetSevTestDlg.h : 头文件
//

#pragma once
#include "afxwin.h"
#include "MySocket.h"
#include "MyRevSocket.h"


// CNetSevTestDlg 对话框
class CNetSevTestDlg : public CDialog
{
// 构造
public:
	CNetSevTestDlg(CWnd* pParent = NULL);	// 标准构造函数

// 对话框数据
	enum { IDD = IDD_NETSEVTEST_DIALOG };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV 支持


// 实现
protected:
	HICON m_hIcon;

	// 生成的消息映射函数
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	void OnTimer(UINT_PTR nIDEvent);
	DECLARE_MESSAGE_MAP()
public:
	CEdit m_port;
	MySocket *m_SevSocket;
	MyRevSocket *m_RevSocket;
	CListBox m_LogCtrl;
	CButton m_Send;
	afx_msg void OnBnClickedButton1();
	afx_msg void OnBnClickedOk();
	CButton m_Creat;
	bool state_RevSocket;
	CEdit m_duankou;
};
