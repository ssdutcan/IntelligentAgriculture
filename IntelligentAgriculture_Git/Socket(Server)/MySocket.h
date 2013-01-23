#pragma once
#include "MyRevSocket.h"
class CNetSevTestDlg;


// MySocket ÃüÁîÄ¿±ê

class MySocket : public CSocket
{
public:
	MySocket();
	virtual ~MySocket();
public:
	MySocket(CNetSevTestDlg *Dlg);
	CNetSevTestDlg *m_Dlg;
	virtual void OnAccept(int nErrorCode);
	virtual void OnClose(int nErrorCode);
//	virtual void AssertValid() const;
};


