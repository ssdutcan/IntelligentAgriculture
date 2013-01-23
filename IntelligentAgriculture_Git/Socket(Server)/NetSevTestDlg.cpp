// NetSevTestDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "NetSevTest.h"
#include "NetSevTestDlg.h"
#include ".\netsevtestdlg.h"
#include <complex>
#include <iostream>


#ifdef _DEBUG
#define new DEBUG_NEW
#endif

CString LongToString(long i);		//自定义函数，将int转化为CString
long StringToLong(CString cstemp);	//自定义函数，将CString转化为Long




long StringToLong(CString cstemp)		//自定义函数，将CString转化为Long
{
	int i,j,k,l;
	long lltemp;

	j=cstemp.Find (".");
	k=cstemp.GetLength ();
	lltemp=0;
	l=0;
	if(j==-1)		//没有小数点
		for(i=0;i<k;i++)
			lltemp=lltemp*10+cstemp.GetAt (i)-0x30;
	else
	{
		for(i=0;i<j;i++)
			lltemp=lltemp*10+cstemp.GetAt (i)-0x30;
		for(i=j+1;i<k;i++)
			l=l*10+cstemp.GetAt (i)-0x30;
		lltemp=lltemp*100+l*pow((double)10,j+3-k);
	}



	return lltemp;
}


CString LongToString(long i)			//自定义函数，将int转化为CString
{
	CString cstemp;
	char ctemp;
	int j;

	cstemp="";
	while(i>9)
	{
		j=i%10;
		ctemp=j+0x30;
		cstemp=ctemp+cstemp;
		i=i/10;
	}
	ctemp=i+0x30;
	cstemp=ctemp+cstemp;

	return cstemp;
}

// 用于应用程序“关于”菜单项的 CAboutDlg 对话框

class CAboutDlg : public CDialog
{
public:
	CAboutDlg();

// 对话框数据
	enum { IDD = IDD_ABOUTBOX };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

// 实现
protected:
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialog(CAboutDlg::IDD)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialog)
END_MESSAGE_MAP()


// CNetSevTestDlg 对话框



CNetSevTestDlg::CNetSevTestDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CNetSevTestDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CNetSevTestDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_EDIT1, m_port);
	DDX_Control(pDX, IDC_LIST1, m_LogCtrl);
	DDX_Control(pDX, IDC_BUTTON1, m_Send);
	DDX_Control(pDX, IDOK, m_Creat);
	DDX_Control(pDX, IDC_EDIT2, m_duankou);
}

BEGIN_MESSAGE_MAP(CNetSevTestDlg, CDialog)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
	ON_BN_CLICKED(IDC_BUTTON1, OnBnClickedButton1)
	ON_BN_CLICKED(IDOK, OnBnClickedOk)
	ON_WM_TIMER()
END_MESSAGE_MAP()


// CNetSevTestDlg 消息处理程序

BOOL CNetSevTestDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// 将\“关于...\”菜单项添加到系统菜单中。

	// IDM_ABOUTBOX 必须在系统命令范围内。
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		CString strAboutMenu;
		strAboutMenu.LoadString(IDS_ABOUTBOX);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// 设置此对话框的图标。当应用程序主窗口不是对话框时，框架将自动
	//  执行此操作
	SetIcon(m_hIcon, TRUE);			// 设置大图标
	SetIcon(m_hIcon, FALSE);		// 设置小图标

	// TODO: 在此添加额外的初始化代码
	state_RevSocket=false;
	m_duankou.SetWindowText ("10000");
	m_duankou.SetLimitText (5);

	SetTimer(1, 10000, NULL);
	
	return TRUE;  // 除非设置了控件的焦点，否则返回 TRUE
}

void CNetSevTestDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialog::OnSysCommand(nID, lParam);
	}
}

// 如果向对话框添加最小化按钮，则需要下面的代码
//  来绘制该图标。对于使用文档/视图模型的 MFC 应用程序，
//  这将由框架自动完成。

void CNetSevTestDlg::OnPaint() 
{
	if (IsIconic())
	{
		CPaintDC dc(this); // 用于绘制的设备上下文

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// 使图标在工作矩形中居中
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// 绘制图标
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

//当用户拖动最小化窗口时系统调用此函数取得光标显示。
HCURSOR CNetSevTestDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}

void CNetSevTestDlg::OnBnClickedButton1()
{
	// TODO: 在此添加控件通知处理程序代码
	CString str;
	m_port.GetWindowText (str);
	if(str.GetLength ()==0)
	{
		AfxMessageBox("空信息不发送");
	}
	else
	{
		m_LogCtrl.AddString ("你发送的信息如下");
		m_LogCtrl.AddString (str);
		m_LogCtrl.SetCurSel (m_LogCtrl.GetCount ()-1);
		m_RevSocket->Send (LPCTSTR(str),str.GetLength (),0);
	

//		*(m_RevSocket->m_ArOut )<<str;
//		m_RevSocket->m_ArOut->Flush ();
	}

}

void CNetSevTestDlg::OnTimer(UINT_PTR nIDEvent)
{
	switch(nIDEvent)
	{
	case 1:
		if(state_RevSocket==true)
		{
			OnBnClickedButton1();
		}
		break;
	default:
		break;
	}
}

void CNetSevTestDlg::OnBnClickedOk()
{
	// TODO: 在此添加控件通知处理程序代码
	//OnOK();
	CString cstemp,cstemp1;
	int i;
	m_Creat.GetWindowText (cstemp);
	m_duankou.GetWindowText (cstemp1);

	
	if(cstemp=="建立服务器")
	{
		if(cstemp1!="")
		{
			i=StringToLong(cstemp1);
			if(i>=1024&&i<=65535)
			{
				if(m_SevSocket=new MySocket(this))
				{
					if(m_SevSocket->Create (i, SOCK_STREAM))    //TCP
					//if(m_SevSocket->Create (i, SOCK_DGRAM))    //UDP
					{
						m_LogCtrl.AddString ("服务器建立,等待连接……");
						m_LogCtrl.SetCurSel (m_LogCtrl.GetCount ()-1);
						m_Creat.SetWindowText ("销毁服务器");
						m_SevSocket->Listen ();
						
					}
					else
					{
						m_LogCtrl.AddString ("初始化失败，请重新启动程序");
						m_LogCtrl.SetCurSel (m_LogCtrl.GetCount ()-1);
						delete m_SevSocket;
					}
				}
				else
				{
					m_LogCtrl.AddString ("初始化失败，请重新启动程序");
					m_LogCtrl.SetCurSel (m_LogCtrl.GetCount ()-1);
				}
			}
			else
			{
				AfxMessageBox("请设置合适的端口值：1024-65535");
			}
		}
		else
		{
			AfxMessageBox("请设置合适的端口值：1024-65535");
		}
	}
	else if(cstemp=="销毁服务器")
	{
		m_SevSocket->Close ();
		m_LogCtrl.AddString ("服务器已被销毁");
		m_LogCtrl.SetCurSel (m_LogCtrl.GetCount ()-1);
//		delete m_SevSocket;
		if(state_RevSocket==true)
            m_RevSocket->Close ();
//		delete m_RevSocket;
		m_Creat.SetWindowText ("建立服务器");
		m_Send.EnableWindow (false);

	}
}
