// NetSevTestDlg.h : ͷ�ļ�
//

#pragma once
#include "afxwin.h"
#include "MySocket.h"
#include "MyRevSocket.h"


// CNetSevTestDlg �Ի���
class CNetSevTestDlg : public CDialog
{
// ����
public:
	CNetSevTestDlg(CWnd* pParent = NULL);	// ��׼���캯��

// �Ի�������
	enum { IDD = IDD_NETSEVTEST_DIALOG };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV ֧��


// ʵ��
protected:
	HICON m_hIcon;

	// ���ɵ���Ϣӳ�亯��
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
