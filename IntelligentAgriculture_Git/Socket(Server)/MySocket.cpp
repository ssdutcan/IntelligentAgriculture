// MySocket.cpp : ʵ���ļ�
//

#include "stdafx.h"
#include "NetSevTest.h"
#include "MySocket.h"
#include "NetSevTestDlg.h"
#include ".\mysocket.h"

// MySocket

MySocket::MySocket(CNetSevTestDlg *Dlg)
{
	m_Dlg=Dlg;
}

MySocket::~MySocket()
{
}


// MySocket ��Ա����

void MySocket::OnAccept(int nErrorCode)
{
	// TODO: �ڴ����ר�ô����/����û���
	MyRevSocket *tempSock;
//	if(nErrorCode==0)
//	{
	if(tempSock =new MyRevSocket(this->m_Dlg ))
	{
		if(Accept(*tempSock))
		{
//				tempSock->m_File =new CSocketFile(tempSock);
//				tempSock->m_ArIn =new CArchive(tempSock->m_File ,CArchive::load );
//				tempSock->m_ArOut =new CArchive(tempSock->m_File ,CArchive::store );
			m_Dlg->m_RevSocket =tempSock;
			tempSock=NULL;
			m_Dlg->m_LogCtrl .AddString ("���ӳɹ������Կ�ʼ������Ϣ");
			m_Dlg->m_LogCtrl .SetCurSel (m_Dlg->m_LogCtrl .GetCount ()-1);
			m_Dlg->state_RevSocket=true;
			m_Dlg->m_Send.EnableWindow (true);
		}
		else
		{
			m_Dlg->m_LogCtrl .AddString ("�ͻ��˵�ǰ���ӳ���ʧ��");
			m_Dlg->m_LogCtrl .SetCurSel (m_Dlg->m_LogCtrl .GetCount ()-1);
			delete tempSock;
		}
	}
	else
	{
		m_Dlg->m_LogCtrl .AddString ("�����׽��ֳ�ʼ��ʧ��");
		m_Dlg->m_LogCtrl .SetCurSel (m_Dlg->m_LogCtrl .GetCount ()-1);
	}
//	}
//	else
//	{
//		m_Dlg->m_LogCtrl.AddString ("�ͻ������ӶϿ�");
//		m_Dlg->m_LogCtrl .SetCurSel (m_Dlg->m_LogCtrl .GetCount ()-1);
//		m_Dlg->state_RevSocket=false;
//	}




	CSocket::OnAccept(nErrorCode);
}

void MySocket::OnClose(int nErrorCode)
{
	// TODO: �ڴ����ר�ô����/����û���

	CSocket::OnClose(nErrorCode);
}

//void MySocket::AssertValid() const
//{
//	CSocket::AssertValid();
//
//	// TODO: �ڴ����ר�ô����/����û���
//}
