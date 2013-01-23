// MySocket.cpp : 实现文件
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


// MySocket 成员函数

void MySocket::OnAccept(int nErrorCode)
{
	// TODO: 在此添加专用代码和/或调用基类
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
			m_Dlg->m_LogCtrl .AddString ("连接成功，可以开始传递信息");
			m_Dlg->m_LogCtrl .SetCurSel (m_Dlg->m_LogCtrl .GetCount ()-1);
			m_Dlg->state_RevSocket=true;
			m_Dlg->m_Send.EnableWindow (true);
		}
		else
		{
			m_Dlg->m_LogCtrl .AddString ("客户端当前连接尝试失败");
			m_Dlg->m_LogCtrl .SetCurSel (m_Dlg->m_LogCtrl .GetCount ()-1);
			delete tempSock;
		}
	}
	else
	{
		m_Dlg->m_LogCtrl .AddString ("连接套结字初始化失败");
		m_Dlg->m_LogCtrl .SetCurSel (m_Dlg->m_LogCtrl .GetCount ()-1);
	}
//	}
//	else
//	{
//		m_Dlg->m_LogCtrl.AddString ("客户端连接断开");
//		m_Dlg->m_LogCtrl .SetCurSel (m_Dlg->m_LogCtrl .GetCount ()-1);
//		m_Dlg->state_RevSocket=false;
//	}




	CSocket::OnAccept(nErrorCode);
}

void MySocket::OnClose(int nErrorCode)
{
	// TODO: 在此添加专用代码和/或调用基类

	CSocket::OnClose(nErrorCode);
}

//void MySocket::AssertValid() const
//{
//	CSocket::AssertValid();
//
//	// TODO: 在此添加专用代码和/或调用基类
//}
