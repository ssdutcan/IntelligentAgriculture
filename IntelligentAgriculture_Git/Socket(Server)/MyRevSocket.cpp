// MyRevSocket.cpp : ʵ���ļ�
//

#include "stdafx.h"
#include "NetSevTest.h"
#include "MyRevSocket.h"
#include "NetSevTestDlg.h"
#include ".\myrevsocket.h"

// MyRevSocket

MyRevSocket::MyRevSocket(CNetSevTestDlg *Dlg)
{
	m_Dlg=Dlg;
}

MyRevSocket::~MyRevSocket()
{
}


// MyRevSocket ��Ա����

void MyRevSocket::OnReceive(int nErrorCode)
{
	// TODO: �ڴ����ר�ô����/����û���
	CString str;
	char ctemp[1000];
	int i,j=1000;
	i=Receive (ctemp,j,0);
	ctemp[i]='\0';
	str=ctemp;

//	(*m_ArIn)>>str;
//	m_ArIn->ReadString (str);

	m_Dlg->m_LogCtrl .AddString ("�Է�������Ϣ����");
	m_Dlg->m_LogCtrl .AddString (str);
	m_Dlg->m_LogCtrl .SetCurSel (m_Dlg->m_LogCtrl .GetCount ()-1);

	CSocket::OnReceive(nErrorCode);
}

void MyRevSocket::OnClose(int nErrorCode)
{
	// TODO: �ڴ����ר�ô����/����û���
	m_Dlg->m_LogCtrl.AddString ("�ͻ������ӶϿ�");
	m_Dlg->m_LogCtrl .SetCurSel (m_Dlg->m_LogCtrl .GetCount ()-1);
	m_Dlg->state_RevSocket=false;
	m_Dlg->m_Send.EnableWindow (false);

	CSocket::OnClose(nErrorCode);
}
