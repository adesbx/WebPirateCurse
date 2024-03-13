module.exports = function (app) {
  /*
  * Routes
  */
  app.use('/resources', require('./routes/resources.route'));
  app.use('/zrr', require('./routes/zrr.route'));

};
