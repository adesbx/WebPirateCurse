// Generated using webpack-cli https://github.com/webpack/webpack-cli

const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ESLintPlugin = require('eslint-webpack-plugin');
const isProduction = process.env.NODE_ENV == 'production';
var webpack = require('webpack');
const stylesHandler = 'style-loader';



const config = {
    entry: {
        index: './index.html',
        admin: './admin.html',
        main: './src/index.js',
        admin: './src/admin.js',
    },
    output: {
        path: path.resolve(__dirname, 'dist'),
    },
    devServer: {
        open: true,
        host: 'localhost',
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: 'index.html',
            chunks: ['main']
        }),
        new HtmlWebpackPlugin({
            template: 'admin.html',
            filename: 'admin.html',
            chunks: ['admin']
        }),
        new ESLintPlugin(),
        // Add your plugins here
        new webpack.DefinePlugin({
            'process.env.PATHAPI': 'http://localhost:3376',//Surement ça mais ça fonctionne pas pour le moment
        }),
        // Learn more about plugins from https://webpack.js.org/configuration/plugins/
    ],
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/i,
                loader: 'babel-loader',
            },
            {
                test: /\.html$/i,
                loader: "html-loader",
            },
            {
                test: /\.css$/i,
                use: [stylesHandler,'css-loader'],
            },
            {
                test: /\.(eot|svg|ttf|woff|woff2|png|jpg|gif)$/i,
                type: 'asset',
            },

            // Add your rules for custom modules here
            // Learn more about loaders from https://webpack.js.org/loaders/
        ],
    },
};

module.exports = () => {
    if (isProduction) {
        config.mode = 'production';
        
    } else {
        config.mode = 'development';
    }
    return config;
};
