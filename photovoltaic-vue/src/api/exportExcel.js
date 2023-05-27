import exportExcel from '@/utils/export'

export function exportExcels(data) {
  return exportExcel({
    url: '/subsidy/export',
    method: 'get',
    responseType: 'blob'
    // params: { id: params }
  })
}