import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MagacinskaKarticaComponent } from '../list/magacinska-kartica.component';
import { MagacinskaKarticaDetailComponent } from '../detail/magacinska-kartica-detail.component';
import { MagacinskaKarticaUpdateComponent } from '../update/magacinska-kartica-update.component';
import { MagacinskaKarticaRoutingResolveService } from './magacinska-kartica-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const magacinskaKarticaRoute: Routes = [
  {
    path: '',
    component: MagacinskaKarticaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MagacinskaKarticaDetailComponent,
    resolve: {
      magacinskaKartica: MagacinskaKarticaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MagacinskaKarticaUpdateComponent,
    resolve: {
      magacinskaKartica: MagacinskaKarticaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MagacinskaKarticaUpdateComponent,
    resolve: {
      magacinskaKartica: MagacinskaKarticaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(magacinskaKarticaRoute)],
  exports: [RouterModule],
})
export class MagacinskaKarticaRoutingModule {}
