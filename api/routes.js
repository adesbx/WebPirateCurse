import resources from './routes/resources.route.js'
import zrr from './routes/zrr.route.js'
import admin from './routes/admin.route.js'

export default function (app) {
  /*
  * Routes
  */
  app.use('/api/resources', resources);
  app.use('/api/zrr', zrr);
  app.use('/admin', admin);

};
