import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MagacinComponent } from '../list/magacin.component';
import { MagacinDetailComponent } from '../detail/magacin-detail.component';
import { MagacinUpdateComponent } from '../update/magacin-update.component';
import { MagacinRoutingResolveService } from './magacin-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const magacinRoute: Routes = [
  {
    path: '',
    component: MagacinComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MagacinDetailComponent,
    resolve: {
      magacin: MagacinRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MagacinUpdateComponent,
    resolve: {
      magacin: MagacinRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MagacinUpdateComponent,
    resolve: {
      magacin: MagacinRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(magacinRoute)],
  exports: [RouterModule],
})
export class MagacinRoutingModule {}
