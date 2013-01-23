#pragma once
class CNetSevTestDlg;


// MyRevSocket ÃüÁîÄ¿±ê

class MyRevSocket : public CSocket
{
public:
	MyRevSocket();
	virtual ~MyRevSocket();
public:
	MyRevSocket(CNetSevTestDlg *Dlg);
	CNetSevTestDlg *m_Dlg;
	CSocketFile *m_File;
	CArchive *m_ArIn;
	CArchive *m_ArOut;

	virtual void OnReceive(int nErrorCode);
	virtual void OnClose(int nErrorCode);
};


