// NetSevTest.h : PROJECT_NAME Ӧ�ó������ͷ�ļ�
//

#pragma once

#ifndef __AFXWIN_H__
	#error �ڰ������� PCH �Ĵ��ļ�֮ǰ������stdafx.h��
#endif

#include "resource.h"		// ������


// CNetSevTestApp:
// �йش����ʵ�֣������ NetSevTest.cpp
//

class CNetSevTestApp : public CWinApp
{
public:
	CNetSevTestApp();

// ��д
	public:
	virtual BOOL InitInstance();

// ʵ��

	DECLARE_MESSAGE_MAP()
};

extern CNetSevTestApp theApp;
