export function getYearWeeks(year) {
  const startDate = new Date(Date.UTC(year, 0, 1));
  const endDate = new Date(Date.UTC(year, 11, 31));
  const weeks = [];

  let currentDate = startDate;
  while (currentDate <= endDate) {
    const week = [];
    for (let i = 0; i < 7; i++) {
      if (currentDate <= endDate) {
        week.push(new Date(currentDate));
        currentDate.setUTCDate(currentDate.getUTCDate() + 1);
      } else {
        week.push(null);
      }
    }
    weeks.push(week);
  }
  return weeks;
}
  
export function getDayOfYear(date) {
  const start = new Date(Date.UTC(date.getUTCFullYear(), 0, 1));
  const diff = date - start;
  const oneDay = 1000 * 60 * 60 * 24;
  return Math.floor(diff / oneDay);
}
  
export function formatDate(date) {
  if (!date) return '';
  const options = { year: 'numeric', month: '2-digit', day: '2-digit', timeZone: 'Asia/Seoul' };
  return new Intl.DateTimeFormat('en-CA', options).format(new Date(date.toLocaleString('en-US', { timeZone: 'Asia/Seoul' })));
}
  