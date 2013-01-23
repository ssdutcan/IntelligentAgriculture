// NetSevTestDlg.cpp : ʵ���ļ�
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

CString LongToString(long i);		//�Զ��庯������intת��ΪCString
long StringToLong(CString cstemp);	//�Զ��庯������CStringת��ΪLong




long StringToLong(CString cstemp)		//�Զ��庯������CStringת��ΪLong
{
	int i,j,k,l;
	long lltemp;

	j=cstemp.Find (".");
	k=cstemp.GetLength ();
	lltemp=0;
	l=0;
	if(j==-1)		//û��С����
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


CString LongToString(long i)			//�Զ��庯������intת��ΪCString
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

// ����Ӧ�ó��򡰹��ڡ��˵���� CAboutDlg �Ի���

class CAboutDlg : public CDialog
{
public:
	CAboutDlg();

// �Ի�������
	enum { IDD = IDD_ABOUTBOX };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV ֧��

// ʵ��
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


// CNetSevTestDlg �Ի���



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


// CNetSevTestDlg ��Ϣ�������

BOOL CNetSevTestDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// ��\������...\���˵�����ӵ�ϵͳ�˵��С�

	// IDM_ABOUTBOX ������ϵͳ���Χ�ڡ�
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

	// ���ô˶Ի����ͼ�ꡣ��Ӧ�ó��������ڲ��ǶԻ���ʱ����ܽ��Զ�
	//  ִ�д˲���
	SetIcon(m_hIcon, TRUE);			// ���ô�ͼ��
	SetIcon(m_hIcon, FALSE);		// ����Сͼ��

	// TODO: �ڴ���Ӷ���ĳ�ʼ������
	state_RevSocket=false;
	m_duankou.SetWindowText ("10000");
	m_duankou.SetLimitText (5);

	SetTimer(1, 10000, NULL);
	
	return TRUE;  // ���������˿ؼ��Ľ��㣬���򷵻� TRUE
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

// �����Ի��������С����ť������Ҫ����Ĵ���
//  �����Ƹ�ͼ�ꡣ����ʹ���ĵ�/��ͼģ�͵� MFC Ӧ�ó���
//  �⽫�ɿ���Զ���ɡ�

void CNetSevTestDlg::OnPaint() 
{
	if (IsIconic())
	{
		CPaintDC dc(this); // ���ڻ��Ƶ��豸������

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// ʹͼ���ڹ��������о���
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// ����ͼ��
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

//���û��϶���С������ʱϵͳ���ô˺���ȡ�ù����ʾ��
HCURSOR CNetSevTestDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}

void CNetSevTestDlg::OnBnClickedButton1()
{
	// TODO: �ڴ���ӿؼ�֪ͨ����������
	CString str;
	m_port.GetWindowText (str);
	if(str.GetLength ()==0)
	{
		AfxMessageBox("����Ϣ������");
	}
	else
	{
		m_LogCtrl.AddString ("�㷢�͵���Ϣ����");
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
	// TODO: �ڴ���ӿؼ�֪ͨ����������
	//OnOK();
	CString cstemp,cstemp1;
	int i;
	m_Creat.GetWindowText (cstemp);
	m_duankou.GetWindowText (cstemp1);

	
	if(cstemp=="����������")
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
						m_LogCtrl.AddString ("����������,�ȴ����ӡ���");
						m_LogCtrl.SetCurSel (m_LogCtrl.GetCount ()-1);
						m_Creat.SetWindowText ("���ٷ�����");
						m_SevSocket->Listen ();
						
					}
					else
					{
						m_LogCtrl.AddString ("��ʼ��ʧ�ܣ���������������");
						m_LogCtrl.SetCurSel (m_LogCtrl.GetCount ()-1);
						delete m_SevSocket;
					}
				}
				else
				{
					m_LogCtrl.AddString ("��ʼ��ʧ�ܣ���������������");
					m_LogCtrl.SetCurSel (m_LogCtrl.GetCount ()-1);
				}
			}
			else
			{
				AfxMessageBox("�����ú��ʵĶ˿�ֵ��1024-65535");
			}
		}
		else
		{
			AfxMessageBox("�����ú��ʵĶ˿�ֵ��1024-65535");
		}
	}
	else if(cstemp=="���ٷ�����")
	{
		m_SevSocket->Close ();
		m_LogCtrl.AddString ("�������ѱ�����");
		m_LogCtrl.SetCurSel (m_LogCtrl.GetCount ()-1);
//		delete m_SevSocket;
		if(state_RevSocket==true)
            m_RevSocket->Close ();
//		delete m_RevSocket;
		m_Creat.SetWindowText ("����������");
		m_Send.EnableWindow (false);

	}
}
